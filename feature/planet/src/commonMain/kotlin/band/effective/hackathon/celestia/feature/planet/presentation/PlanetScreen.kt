package band.effective.hackathon.celestia.feature.planet.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Composable function that displays the planet screen.
 *
 * @param planet The recommended planet to display
 * @param onRestartQuiz Callback to be invoked when the user wants to restart the quiz
 */
@Composable
fun PlanetScreen(
    planet: RecommendedPlanet,
    onRestartQuiz: () -> Unit,
) {
    val viewModel = koinViewModel<PlanetViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(planet) {
        viewModel.setPlanet(planet)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PlanetEffect.RestartQuiz -> onRestartQuiz()
            }
        }
    }

    PlanetScreenContent(
        state = state,
        onRestartQuizClick = viewModel::onRestartQuizClick,
    )
}

@Composable
private fun PlanetScreenContent(
    state: PlanetState,
    onRestartQuizClick: () -> Unit,
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.width(48.dp).height(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            } else if (state.error != null) {
                Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            } else {
                state.planet?.let { planet ->
                    // Planet title
                    Text(
                        text = planet.planetName,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Planet description
                    Text(
                        text = planet.description,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Restart quiz button
                    Button(
                        onClick = onRestartQuizClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Restart Quiz")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PlanetScreenPreview() {
    MaterialTheme {
        PlanetScreenContent(
            state = PlanetState(
                planet = RecommendedPlanet(
                    planetName = "Earth",
                    description = "Earth is the third planet from the Sun and the only astronomical object known to harbor life.",
                    type = 1,
                )
            ),
            onRestartQuizClick = {},
        )
    }
}

@Preview
@Composable
fun PlanetScreenLoadingPreview() {
    MaterialTheme {
        PlanetScreenContent(
            state = PlanetState(isLoading = true),
            onRestartQuizClick = {},
        )
    }
}