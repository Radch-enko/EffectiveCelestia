package band.effective.hackathon.celestia.feature.planet.presentation

import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet


/**
 * Represents the state of the Planet UI.
 *
 * @property planet The recommended planet to display
 * @property isLoading Whether the planet data is currently loading
 * @property error Error message to display, or null if there is no error
 */
data class PlanetState(
    val planet: RecommendedPlanet? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)