package band.effective.hackathon.celestia.feature.quiz.data.datasource

import band.effective.hackathon.celestia.core.data.network.HttpException
import band.effective.hackathon.celestia.core.data.network.postAsEither
import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.map
import band.effective.hackathon.celestia.core.domain.functional.mapError
import band.effective.hackathon.celestia.core.domain.functional.success
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptErrorResponse
import band.effective.hackathon.celestia.feature.quiz.data.model.CompletionOptionsDto
import band.effective.hackathon.celestia.feature.quiz.data.model.GptErrorDto
import band.effective.hackathon.celestia.feature.quiz.data.model.GptRequest
import band.effective.hackathon.celestia.feature.quiz.data.model.GptResponse
import band.effective.hackathon.celestia.feature.quiz.data.model.Message
import band.effective.hackathon.celestia.feature.quiz.data.model.RecommendedPlanetDto
import band.effective.hackathon.celestia.feature.quiz.data.utils.toJsonString
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.utils.io.InternalAPI
import kotlin.random.Random
import kotlinx.serialization.json.Json

private const val GPT_REQUEST_URL = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"
private const val GPT_PROMPT =
    """Исходя из ответов пользователя опередели какая он планета. Ответ сформируй в формате json: { "planetName": "", "description": "", "type": 1 }. Поле Type - это размер планеты (возможные значения от 1 до 3) """

/**
 * Implementation of RemoteDataSource using Ktor client
 */
class KtorRemoteDataSource(
    private val httpClient: HttpClient,
    private val gptApiKey: String,
    private val gptModel: String,
) : RemoteDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Sends user answers to ChatGPT for analysis
     *
     * @param userAnswers Map of question IDs to selected answers
     * @return Either containing the analysis from ChatGPT or an error
     */
    @OptIn(InternalAPI::class)
    override suspend fun sendAnswersToChatGpt(userAnswers: Map<String, String>): Either<GptErrorDto, RecommendedPlanetDto> {
        // Format the answers for the request
        val formattedAnswers = userAnswers.entries.joinToString("\n") { (questionId, answerText) ->
            "$questionId: $answerText"
        }
        return success(
            RecommendedPlanetDto(
                planetName = "Test planet name",
                description = "Planet description",
                type = Random.nextInt(1, 3)
            )
        )
        // Create the request body
        val requestBody = GptRequest(
            modelUri = "gpt://$gptModel/yandexgpt",
            completionOptions = CompletionOptionsDto.default,
            messages = listOf(
                Message(
                    role = "system",
                    text = GPT_PROMPT
                ),
                Message(
                    role = "user",
                    text = formattedAnswers
                )
            ),
        )

        // Make the API call using the Either extension
        return httpClient.postAsEither<GptResponse> {
            post {
                url(GPT_REQUEST_URL)
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $gptApiKey")
                }
                //body = requestBody
                setBody(requestBody)
            }
        }.map { chatGptResponse ->
            // Extract content from the response
            val content = chatGptResponse.result.alternatives.first().message.toJsonString()
            try {
                val recommendedPlanetDto = json.decodeFromString<RecommendedPlanetDto>(content)
                return success(recommendedPlanetDto)
            } catch (e: Exception) {
                error(e)
            }
        }.mapError(::toGptError)
    }

    private fun toGptError(throwable: Throwable): GptErrorDto {
        return when (throwable) {
            is HttpException -> { // Try to parse the error response
                try {
                    val errorResponse = json.decodeFromString<ChatGptErrorResponse>(throwable.message)
                    errorResponse.error
                } catch (e: Exception) { // If parsing fails, create a generic error
                    Napier.e("Failed to parse error response", e)
                    GptErrorDto(
                        message = throwable.message,
                        type = "http_error",
                        code = throwable.status.toString()
                    )
                }
            }

            else -> { // For other exceptions, create a generic error
                GptErrorDto(
                    message = throwable.message ?: "Unknown error",
                    type = "unknown_error"
                )
            }
        }
    }
}

