package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CompletionOptions(
    val stream: Boolean,
    val temperature: Double,
    val maxTokens: Int,
    val reasoningOptions: ReasoningOptions,
)

@Serializable
data class ReasoningOptions(
    val mode: String
)