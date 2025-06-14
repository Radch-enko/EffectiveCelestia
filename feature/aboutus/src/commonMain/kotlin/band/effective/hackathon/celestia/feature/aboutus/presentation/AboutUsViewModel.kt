package band.effective.hackathon.celestia.feature.aboutus.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the About Us feature.
 */
class AboutUsViewModel : ViewModel() {

    private val mutableState = MutableStateFlow(AboutUsState())
    val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<AboutUsEffect>()
    val effect = mutableEffect.asSharedFlow()

    /**
     * Handles the vote button click.
     */
    fun onVoteClick() {
        viewModelScope.launch {
            mutableEffect.emit(AboutUsEffect.Vote)
        }
    }
}