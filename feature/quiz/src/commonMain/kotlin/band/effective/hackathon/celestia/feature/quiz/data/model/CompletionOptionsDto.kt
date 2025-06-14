package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CompletionOptionsDto(
    val stream: Boolean,
    val temperature: Double,
    val maxTokens: Int,
    val reasoningOptions: ReasoningOptionsDto,
) {
    companion object {
        val default = CompletionOptionsDto(
            stream = false,
            temperature = 0.6,
            maxTokens = 2000,
            reasoningOptions = ReasoningOptionsDto(
                mode = "DISABLED",
            )
        )
    }
}

@Serializable
data class ReasoningOptionsDto(
    val mode: String
)