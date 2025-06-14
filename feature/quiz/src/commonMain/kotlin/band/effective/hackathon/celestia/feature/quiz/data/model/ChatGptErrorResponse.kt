package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

/**
 * Data class representing the error response from the ChatGPT API
 */
@Serializable
data class ChatGptErrorResponse(
    val error: GptErrorDto
)