package band.effective.hackathon.celestia.feature.quiz.presentation

import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Represents the state of the Quiz UI.
 *
 * @property currentQuestion The current question being displayed, or null if no question is loaded
 * @property isLoading Whether the quiz is currently loading
 * @property error Error message to display, or null if there is no error
 * @property step Current step/progress in the quiz
 * @property userAnswers Map of question IDs to selected answers
 * @property chatGptResult Result from ChatGPT analysis, or null if not available
 * @property recommendedPlanet Recommended planet based on user answers, or null if not available
 */
data class QuizState(
    val currentQuestion: Question? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val step: Int = 0,
    val userAnswers: Map<String, Answer> = emptyMap(),
    val chatGptResult: String? = null,
    val recommendedPlanet: String? = null,
)
