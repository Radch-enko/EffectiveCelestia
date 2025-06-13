package band.effective.hackathon.celestia.feature.quiz.presentation

/**
 * Represents UI effects for the Quiz feature.
 */
sealed interface QuizEffect {
    /**
     * Indicates that the quiz has been completed.
     */
    object QuizCompleted : QuizEffect
    
    /**
     * Indicates that the user should navigate to the next question.
     *
     * @property nextQuestionId The ID of the next question to navigate to
     */
    data class NavigateToNextQuestion(val nextQuestionId: Int) : QuizEffect
}