package band.effective.hackathon.celestia.feature.quiz.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RecommendedPlanetDto(
    val planetName: String,
    val description: String,
    val type: Int,
)