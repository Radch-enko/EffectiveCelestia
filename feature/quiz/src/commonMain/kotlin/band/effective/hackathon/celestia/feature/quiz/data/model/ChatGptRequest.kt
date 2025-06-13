package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatGptRequest(
    val modelUri: String,
    val messages: List<Message>,
    val completionOptions: CompletionOptions,
)
