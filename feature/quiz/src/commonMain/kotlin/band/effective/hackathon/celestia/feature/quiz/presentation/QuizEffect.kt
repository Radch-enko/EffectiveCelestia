package band.effective.hackathon.celestia.feature.quiz.presentation

import band.effective.hackathon.celestia.feature.quiz.domain.model.RecommendedPlanet

/**
 * Represents UI effects for the Quiz feature.
 */
sealed interface QuizEffect {
    /**
     * Indicates that the quiz has been completed.
     */
    data class QuizCompleted(val planet: RecommendedPlanet) : QuizEffect
}
