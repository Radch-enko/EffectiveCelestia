package band.effective.hackathon.celestia.feature.planet.presentation

import androidx.lifecycle.ViewModel
import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for the Planet screen.
 */
class PlanetViewModel : ViewModel() {

    private val _state = MutableStateFlow(PlanetState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PlanetEffect>()
    val effect = _effect.asSharedFlow()

    /**
     * Sets the recommended planet to display.
     *
     * @param planet The recommended planet
     */
    fun setPlanet(planet: RecommendedPlanet) {
        _state.update { it.copy(planet = planet) }
    }

    /**
     * Handles the restart quiz button click.
     */
    fun onRestartQuizClick() {
        _effect.tryEmit(PlanetEffect.RestartQuiz)
    }
}