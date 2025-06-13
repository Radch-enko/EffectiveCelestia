package band.effective.hackathon.celestia.feature.quiz.presentation

/**
 * Represents UI effects for the Quiz feature.
 */
sealed interface QuizEffect {
    /**
     * Indicates that the quiz has been completed.
     */
    object QuizCompleted : QuizEffect
}
