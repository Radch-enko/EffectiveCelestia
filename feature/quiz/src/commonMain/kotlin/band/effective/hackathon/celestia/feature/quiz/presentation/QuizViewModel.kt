package band.effective.hackathon.celestia.feature.quiz.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.hackathon.celestia.core.domain.functional.fold
import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GenerateWhichPlanetUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.HandleAnswerSelectionUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.LoadFirstQuestionUseCase
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.NavigateToPreviousQuestionUseCase
import io.github.aakira.napier.Napier
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
 * @property loadFirstQuestionUseCase Usecase for loading the first question
 * @property navigateToPreviousQuestionUseCase Usecase for navigating to the previous question
 * @property handleAnswerSelectionUseCase Usecase for handling answer selection
 * @property generateWhichPlanetUseCase Usecase for generating planet recommendations
 */
class QuizViewModel(
    private val loadFirstQuestionUseCase: LoadFirstQuestionUseCase,
    private val navigateToPreviousQuestionUseCase: NavigateToPreviousQuestionUseCase,
    private val handleAnswerSelectionUseCase: HandleAnswerSelectionUseCase,
    private val generateWhichPlanetUseCase: GenerateWhichPlanetUseCase,
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

            // Store the user's answer
            val updatedUserAnswers = state.value.userAnswers.toMutableMap().apply {
                put(currentQuestion.text, answer)
            }

            mutableState.update {
                it.copy(userAnswers = updatedUserAnswers)
            }

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

                        is HandleAnswerSelectionUseCase.Output.QuizCompleted ->
                            generatePlanetRecommendation(state.value.userAnswers)
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

    /**
     * Generates a planet recommendation based on user answers
     */
    private fun generatePlanetRecommendation(answers: Map<String, Answer>) {
        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true) }

            val params = GenerateWhichPlanetUseCase.Params(
                userAnswers = answers
            )

            generateWhichPlanetUseCase(params).fold(
                onError = { error ->
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to generate planet recommendation: ${error.message}"
                        )
                    }
                },
                onSuccess = { output ->
                    Napier.i("QuizViewModel: Successfully generated planet recommendation: ${output.planet}")
                    mutableState.update {
                        it.copy(
                            recommendedPlanet = output.planet.planetName,
                            isLoading = false
                        )
                    }
                }
            )
        }
    }
}
