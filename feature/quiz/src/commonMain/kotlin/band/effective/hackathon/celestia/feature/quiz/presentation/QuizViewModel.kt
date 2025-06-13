package band.effective.hackathon.celestia.feature.quiz.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel for the Quiz feature.
 *
 * @property quizRepository Repository for accessing quiz data
 */
class QuizViewModel(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val mutableState = MutableStateFlow(QuizState(isLoading = true))
    val state = mutableState.asStateFlow()

    private val mutableEffect = MutableSharedFlow<QuizEffect>()
    val effect = mutableEffect.asSharedFlow()

    private val totalQuestions = 4

    init {
        loadFirstQuestion()
    }

    /**
     * Loads the first question of the quiz.
     */
    private fun loadFirstQuestion() {
        viewModelScope.launch {
            try {
                val questions = quizRepository.getQuestions()
                if (questions.isNotEmpty()) {
                    mutableState.update {
                        it.copy(
                            currentQuestion = questions.first(),
                            isLoading = false,
                            error = null
                        )
                    }
                } else {
                    mutableState.update {
                        it.copy(
                            isLoading = false,
                            error = "No questions available"
                        )
                    }
                }
            } catch (e: Exception) {
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load questions: ${e.message}"
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
            // If this is the last question, complete the quiz
            if (currentQuestion.id >= totalQuestions) {
                mutableEffect.emit(QuizEffect.QuizCompleted)
                return@launch
            }

            // Otherwise, load the next question
            try {
                val nextQuestionId = currentQuestion.id + 1
                val nextQuestion = quizRepository.getQuestionById(nextQuestionId)

                if (nextQuestion != null) {
                    mutableState.update {
                        it.copy(
                            currentQuestion = nextQuestion,
                            isLoading = false,
                            error = null
                        )
                    }
                    mutableEffect.emit(QuizEffect.NavigateToNextQuestion(nextQuestionId))
                } else {
                    mutableEffect.emit(QuizEffect.QuizCompleted)
                }
            } catch (e: Exception) {
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load next question: ${e.message}"
                    )
                }
            }
        }
    }
}