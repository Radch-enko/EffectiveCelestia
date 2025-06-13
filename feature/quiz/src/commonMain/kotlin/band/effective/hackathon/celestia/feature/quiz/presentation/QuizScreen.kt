package band.effective.hackathon.celestia.feature.quiz.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import band.effective.hackathon.celestia.core.ui.components.button.AnswerOption
import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Composable function that displays the quiz screen.
 *
 * @param onQuizCompleted Callback to be invoked when the quiz is completed
 */
@Composable
fun QuizScreen(
    onQuizCompleted: () -> Unit
) {
    val viewModel = koinViewModel<QuizViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is QuizEffect.QuizCompleted -> onQuizCompleted()
                is QuizEffect.NavigateToNextQuestion -> {
                    // Navigation is handled internally by updating the state
                }
            }
        }
    }

    QuizScreenContent(
        state = state,
        onAnswerSelected = viewModel::onAnswerSelected,
    )
}

@Composable
private fun QuizScreenContent(state: QuizState, onAnswerSelected: (Answer) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        } else {
            state.currentQuestion?.let { question ->
                // Question text
                Text(
                    text = question.text,
                    style = MaterialTheme.typography.bodyLarge,
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


@Preview
@Composable
fun QuizScreenPreview() {
    MaterialTheme {
        QuizScreenContent(
            state = QuizState(isLoading = false),
            onAnswerSelected = {},
        )
    }
}
