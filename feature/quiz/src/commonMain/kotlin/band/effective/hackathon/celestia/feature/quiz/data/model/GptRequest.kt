package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GptRequest(
    val modelUri: String,
    val messages: List<Message>,
    val completionOptions: CompletionOptionsDto,
)
