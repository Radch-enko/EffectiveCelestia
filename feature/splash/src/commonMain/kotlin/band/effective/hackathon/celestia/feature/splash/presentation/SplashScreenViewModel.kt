package band.effective.hackathon.celestia.feature.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

private const val PREPARE_EMULATION_TIME = 3000L

class SplashScreenViewModel : ViewModel() {

    private val mutableEffect = MutableSharedFlow<SplashEffect>()
    val effect = mutableEffect.asSharedFlow()

    init {
        emulatePreparing()
    }

    private fun emulatePreparing() = viewModelScope.launch {
        delay(PREPARE_EMULATION_TIME)
        mutableEffect.emit(
            SplashEffect.SplashCompleted
        )
    }
}