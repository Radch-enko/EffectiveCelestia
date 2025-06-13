package band.effective.hackathon.celestia.feature.quiz.presentation

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Represents the state of the Quiz UI.
 *
 * @property currentQuestion The current question being displayed, or null if no question is loaded
 * @property isLoading Whether the quiz is currently loading
 * @property error Error message to display, or null if there is no error
 */
data class QuizState(
    val currentQuestion: Question? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)