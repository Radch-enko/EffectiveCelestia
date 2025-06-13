package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatGptResponse(
    val choices: List<Choice>
)