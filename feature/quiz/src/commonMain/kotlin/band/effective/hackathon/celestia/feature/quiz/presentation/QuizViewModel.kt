package band.effective.hackathon.celestia.feature.quiz.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.HandleAnswerSelectionUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.LoadFirstQuestionUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.NavigateToPreviousQuestionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal const val TOTAL_QUIZ_QUESTIONS_COUNT = 4

/**
 * ViewModel for the Quiz feature.
 *
 * @property loadFirstQuestionUseCase Use case for loading the first question
 * @property navigateToPreviousQuestionUseCase Use case for navigating to the previous question
 * @property handleAnswerSelectionUseCase Use case for handling answer selection
 */
class QuizViewModel(
    private val loadFirstQuestionUseCase: LoadFirstQuestionUseCase,
    private val navigateToPreviousQuestionUseCase: NavigateToPreviousQuestionUseCase,
    private val handleAnswerSelectionUseCase: HandleAnswerSelectionUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow(QuizState(isLoading = true))
    val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<QuizEffect>()
    val effect = mutableEffect.asSharedFlow()

    // Store question history to support going back
    private val questionHistory = mutableListOf<Int>()

    init {
        loadFirstQuestion()
    }

    /**
     * Loads the first question of the quiz.
     */
    private fun loadFirstQuestion() = viewModelScope.launch {
        mutableState.update { it.copy(isLoading = true) }

        loadFirstQuestionUseCase()
            .onSuccess { question ->
                // Add first question to history
                questionHistory.add(question.id)

                mutableState.update {
                    it.copy(
                        currentQuestion = question,
                        isLoading = false,
                        error = null
                    )
                }
            }
            .onFailure { error ->
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load questions: ${error.message}"
                    )
                }
            }

    }

    /**
     * Handles going back to the previous question.
     */
    fun onBackPressed() {
        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true) }

            navigateToPreviousQuestionUseCase(questionHistory)
                .onSuccess { (previousQuestion, updatedHistory) ->
                    // Update history
                    questionHistory.clear()
                    questionHistory.addAll(updatedHistory)

                    mutableState.update {
                        it.copy(
                            currentQuestion = previousQuestion,
                            isLoading = false,
                            error = null,
                            step = state.value.step - 1
                        )
                    }
                }
                .onFailure { error ->
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to load previous question: ${error.message}"
                        )
                    }
                }
        }
    }

    /**
     * Handles the selection of an answer.
     *
     * @param answer The selected answer
     */
    fun onAnswerSelected(answer: Answer) {
        val currentQuestion = state.value.currentQuestion ?: return

        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true) }

            val params = HandleAnswerSelectionUseCase.Params(
                currentQuestion = currentQuestion,
                selectedAnswer = answer,
                questionHistory = questionHistory.toList()
            )

            handleAnswerSelectionUseCase(params)
                .onSuccess { output ->
                    when (output) {
                        is HandleAnswerSelectionUseCase.Output.NextQuestion -> {
                            // Update history
                            questionHistory.clear()
                            questionHistory.addAll(output.updatedHistory)

                            mutableState.update {
                                it.copy(
                                    currentQuestion = output.question,
                                    isLoading = false,
                                    error = null,
                                    step = state.value.step + 1
                                )
                            }
                        }

                        is HandleAnswerSelectionUseCase.Output.QuizCompleted -> {
                            mutableEffect.emit(QuizEffect.QuizCompleted)
                        }
                    }
                }
                .onFailure { error ->
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to process answer: ${error.message}"
                        )
                    }
                }
        }
    }
}