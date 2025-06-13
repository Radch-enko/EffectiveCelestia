package band.effective.hackathon.celestia.feature.quiz.data.datasource

import band.effective.hackathon.celestia.core.data.network.HttpException
import band.effective.hackathon.celestia.core.data.network.postAsEither
import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.map
import band.effective.hackathon.celestia.core.domain.functional.mapError
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptError
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptErrorResponse
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptRequest
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptResponse
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
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
    override suspend fun sendAnswersToChatGpt(userAnswers: Map<Int, String>): Either<ChatGptError, String> {
        // Format the answers for the request
        val formattedAnswers = userAnswers.entries.joinToString("\n") { (questionId, answerText) ->
            "Question $questionId: $answerText"
        }
        // Create the request body
        val requestBody = ChatGptRequest(
            prompt = "Analyze the following quiz answers and provide feedback:\n$formattedAnswers"
        )

        // Make the API call using the Either extension
        return httpClient.postAsEither<ChatGptResponse>("https://api.openai.com/v1/chat/completions") {
            post("https://api.openai.com/v1/chat/completions") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $chatGptApiKey")
                }
                setBody(requestBody)
            }
        }.map { chatGptResponse ->
            // Extract content from the response
            val content = chatGptResponse.choices.firstOrNull()?.message?.content ?: "No analysis available"
            content
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
