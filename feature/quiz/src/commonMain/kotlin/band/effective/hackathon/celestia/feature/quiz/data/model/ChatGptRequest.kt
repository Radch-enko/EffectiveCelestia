package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatGptRequest(
    val model: String = "gpt-3.5-turbo",
    val prompt: String,
    val max_tokens: Int = 500,
    val temperature: Double = 0.7
)



