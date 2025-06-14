package band.effective.hackathon.celestia.feature.planet.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Planet screen.
 */
class PlanetViewModel : ViewModel() {

    private val mutableState = MutableStateFlow(PlanetState())
    val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<PlanetEffect>()
    val effect = mutableEffect.asSharedFlow()

    /**
     * Sets the recommended planet to display.
     *
     * @param planet The recommended planet
     */
    fun setPlanet(planet: RecommendedPlanet) {
        mutableState.update { it.copy(planet = planet) }
    }

    /**
     * Handles the restart quiz button click.
     */
    fun onRestartQuizClick() = viewModelScope.launch {
        mutableEffect.emit(PlanetEffect.RestartQuiz)
    }
}