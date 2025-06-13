package band.effective.hackathon.celestia.feature.quiz.data.datasource

import band.effective.hackathon.celestia.core.data.network.HttpException
import band.effective.hackathon.celestia.core.data.network.postAsEither
import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.map
import band.effective.hackathon.celestia.core.domain.functional.mapError
import band.effective.hackathon.celestia.core.domain.functional.success
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptError
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptErrorResponse
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptRequest
import band.effective.hackathon.celestia.feature.quiz.data.model.CompletionOptions
import band.effective.hackathon.celestia.feature.quiz.data.model.GptResponse
import band.effective.hackathon.celestia.feature.quiz.data.model.Message
import band.effective.hackathon.celestia.feature.quiz.data.model.ReasoningOptions
import band.effective.hackathon.celestia.feature.quiz.data.model.RecommendedPlanetDto
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
import kotlinx.serialization.json.Json


/**
 * Implementation of RemoteDataSource using Ktor client
 */
class KtorRemoteDataSource(
    private val httpClient: HttpClient,
    private val chatGptApiKey: String,
) : RemoteDataSource {

    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Sends user answers to ChatGPT for analysis
     *
     * @param userAnswers Map of question IDs to selected answers
     * @return Either containing the analysis from ChatGPT or an error
     */
    @OptIn(InternalAPI::class)
    override suspend fun sendAnswersToChatGpt(userAnswers: Map<String, String>): Either<ChatGptError, RecommendedPlanetDto> {
        // Format the answers for the request
        val formattedAnswers = userAnswers.entries.joinToString("\n") { (questionId, answerText) ->
            "$questionId: $answerText"
        }
        // Create the request body
        val requestBody = ChatGptRequest(
            modelUri = "gpt://b1gbhqbeo0c8k3p9p15d/yandexgpt",
            completionOptions = CompletionOptions(
                stream = false,
                temperature = 0.6,
                maxTokens = 2000,
                reasoningOptions = ReasoningOptions(
                    mode = "DISABLED",
                )
            ),
            messages = listOf(
                Message(
                    role = "system",
                    text = """Исходя из ответов пользователя опередели какая он планета. Ответ сформируй в формате json: { "planetName": "", "description": "", "type": 1 }. Поле Type - это размер планеты (возможные значения от 1 до 3) """
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
                url("https://llm.api.cloud.yandex.net/foundationModels/v1/completion")
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $chatGptApiKey")
                }
                //body = requestBody
                setBody(requestBody)
            }
        }.map { chatGptResponse ->
            // Extract content from the response
            val content = chatGptResponse.result.alternatives.first().message.text.replace("```", "")
            try {
                val recommendedPlanetDto = json.decodeFromString<RecommendedPlanetDto>(content)
                return success(recommendedPlanetDto)
            } catch (e: Exception) {
                error(e)
            }
        }.mapError { throwable ->
            // Convert Throwable to ChatGptError
            when (throwable) {
                is HttpException -> {
                    try {
                        // Try to parse the error response
                        val errorResponse = json.decodeFromString<ChatGptErrorResponse>(throwable.message)
                        errorResponse.error
                    } catch (e: Exception) {
                        // If parsing fails, create a generic error
                        Napier.e("Failed to parse error response", e)
                        ChatGptError(
                            message = throwable.message,
                            type = "http_error",
                            code = throwable.status.toString()
                        )
                    }
                }

                else -> {
                    // For other exceptions, create a generic error
                    ChatGptError(
                        message = throwable.message ?: "Unknown error",
                        type = "unknown_error"
                    )
                }
            }
        }
    }
}
