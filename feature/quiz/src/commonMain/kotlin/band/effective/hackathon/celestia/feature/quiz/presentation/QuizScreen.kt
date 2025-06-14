package band.effective.hackathon.celestia.feature.quiz.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet
import band.effective.hackathon.celestia.core.ui.components.button.AnswerOption
import band.effective.hackathon.celestia.core.ui.components.button.BackButton
import band.effective.hackathon.celestia.core.ui.components.button.ProgressBar
import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import effectivecelestia.feature.quiz.generated.resources.Res
import effectivecelestia.feature.quiz.generated.resources.quiz_generating_answer
import effectivecelestia.feature.quiz.generated.resources.quiz_unknown_error
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Composable function that displays the quiz screen.
 *
 * @param onQuizCompleted Callback to be invoked when the quiz is completed
 * @param onNavigateAfterLoading Callback to be invoked when navigation after loading should happen
 */
@Composable
fun QuizScreen(
    onQuizCompleted: (RecommendedPlanet) -> Unit,
) {
    val viewModel = koinViewModel<QuizViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is QuizEffect.QuizCompleted -> onQuizCompleted(effect.planet)
            }
        }
    }

    QuizScreenContent(
        state = state,
        onAnswerSelected = viewModel::onAnswerSelected,
        onBackClick = viewModel::onBackPressed,
    )
}

@Composable
private fun QuizScreenContent(
    state: QuizState,
    onAnswerSelected: (Answer) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp + 32.dp)
                    .padding(horizontal = 38.dp, vertical = 16.dp),
            ) {
                if (!state.isLoading) {
                    if (state.step != 0) {
                        BackButton(modifier = Modifier.align(Alignment.CenterStart), onClick = onBackClick)
                    }

                    ProgressBar(
                        modifier = Modifier.align(Alignment.Center).width(120.dp),
                        currentProgress = state.step.toFloat(),
                        max = TOTAL_QUIZ_QUESTIONS_COUNT.toFloat(),
                    )
                }
            }
        },
        containerColor = Color.Transparent,
        contentColor = Color.Transparent,
    ) {
        val coroutineScope = rememberCoroutineScope()
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
                        text = stringResource(Res.string.quiz_generating_answer),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            } else if (state.error != null) {
                Text(
                    text = state.error ?: stringResource(Res.string.quiz_unknown_error),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            } else {
                state.currentQuestion?.let { question ->
                    // Question text
                    Text(
                        text = question.text,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    var selectedItem by remember { mutableStateOf<Answer?>(null) }
                    // Answer options
                    question.answers.forEach { answer ->
                        AnswerOption(
                            modifier = Modifier.fillMaxWidth(),
                            text = answer.text,
                            isSelected = selectedItem == answer,
                            onClick = {
                                coroutineScope.launch {
                                    selectedItem = answer
                                    delay(100)
                                    onAnswerSelected(answer)
                                    selectedItem = null
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun QuizScreenPreview() {
    MaterialTheme {
        QuizScreenContent(
            state = QuizState(isLoading = false),
            onAnswerSelected = {},
            onBackClick = {},
        )
    }
}

@Preview
@Composable
fun QuizScreenLoadingPreview() {
    MaterialTheme {
        QuizScreenContent(
            state = QuizState(isLoading = true),
            onAnswerSelected = {},
            onBackClick = {},
        )
    }
}
