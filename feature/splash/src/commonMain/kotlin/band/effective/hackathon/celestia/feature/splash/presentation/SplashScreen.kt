package band.effective.hackathon.celestia.feature.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.hackathon.celestia.res.logotype
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    onSplashCompleted: () -> Unit,
) {
    val viewModel = koinViewModel<SplashScreenViewModel>()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.SplashCompleted -> onSplashCompleted()
            }
        }
    }

    SplashScreenContent()
}

@Composable
private fun SplashScreenContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(band.effective.hackathon.celestia.res.Res.drawable.logotype),
            contentDescription = null,
            modifier = Modifier.size(120.dp).align(Alignment.Center),
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreenContent()
    }
}