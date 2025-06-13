package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GptResponse(
    val result: GptResult
)

@Serializable
data class GptResult(
    val alternatives: List<GptAlternative>
)

@Serializable
data class GptAlternative(
    val message: GptMessage
)

@Serializable
data class GptMessage(
    val role: String,
    val text: String
)