package band.effective.hackathon.celestia.feature.quiz.domain.usecase

import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Use case for handling the selection of an answer.
 */
interface HandleAnswerSelectionUseCase{

    suspend operator fun invoke(params: Params): Result<Output>
    /**
     * Parameters for the HandleAnswerSelectionUseCase.
     *
     * @property currentQuestion The current question
     * @property selectedAnswer The selected answer
     * @property questionHistory The history of question IDs
     */
    data class Params(
        val currentQuestion: Question,
        val selectedAnswer: Answer,
        val questionHistory: List<Int>
    )

    /**
     * Output of the HandleAnswerSelectionUseCase.
     */
    sealed class Output {
        /**
         * Represents the next question with updated history.
         *
         * @property question The next question
         * @property updatedHistory The updated question history
         */
        data class NextQuestion(
            val question: Question,
            val updatedHistory: List<Int>
        ) : Output()

        /**
         * Indicates that the quiz has been completed.
         */
        object QuizCompleted : Output()
    }
}