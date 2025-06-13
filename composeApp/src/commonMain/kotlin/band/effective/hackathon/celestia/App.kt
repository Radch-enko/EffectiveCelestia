package band.effective.hackathon.celestia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import band.effective.hackathon.celestia.feature.splash.presentation.SplashScreen
import band.effective.hackathon.celestia.feature.test.presentation.TestScreen
import band.effective.hackathon.celestia.theme.AppTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun App() = AppTheme {
    MockNavigation()
}

@Composable
fun MockNavigation() {
    var splashToTest by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1000)
        splashToTest = true
    }

    if (splashToTest) {
        SplashScreen()
    } else {
        TestScreen()
    }
}
