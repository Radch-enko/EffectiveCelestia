package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

/**
 * Data class representing an error from the ChatGPT API
 */
@Serializable
data class ChatGptError(
    val message: String = "",
    val type: String = "",
    val param: String? = null,
    val code: String? = null,
)