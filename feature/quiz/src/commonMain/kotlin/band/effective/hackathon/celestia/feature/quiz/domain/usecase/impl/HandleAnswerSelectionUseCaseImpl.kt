package band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl

import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.HandleAnswerSelectionUseCase
import band.effective.hackathon.celestia.feature.quiz.presentation.TOTAL_QUIZ_QUESTIONS_COUNT
import kotlinx.coroutines.delay

/**
 * Implementation of [HandleAnswerSelectionUseCase] that uses [QuizRepository] to handle answer selection.
 *
 * @property quizRepository Repository for accessing quiz data
 */
class HandleAnswerSelectionUseCaseImpl(
    private val quizRepository: QuizRepository
) : HandleAnswerSelectionUseCase {
    /**
     * Handles the selection of an answer.
     *
     * @param params Parameters containing the current question, selected answer, and question history
     * @return Result containing the next question and updated history, or a completion signal, or an error
     */
    override suspend fun invoke(params: HandleAnswerSelectionUseCase.Params): Result<HandleAnswerSelectionUseCase.Output> {
        return try {
            val currentQuestion = params.currentQuestion
            val questionHistory = params.questionHistory.toMutableList()

            // If this is the last question, complete the quiz
            if (currentQuestion.id >= TOTAL_QUIZ_QUESTIONS_COUNT) {
                // Mock HTTP request
                delay(2000)
                return Result.success(HandleAnswerSelectionUseCase.Output.QuizCompleted)
            }

            // Otherwise, load the next question
            val nextQuestionId = currentQuestion.id + 1
            val nextQuestion = quizRepository.getQuestionById(nextQuestionId)

            if (nextQuestion != null) {
                // Add next question to history
                questionHistory.add(nextQuestion.id)
                Result.success(HandleAnswerSelectionUseCase.Output.NextQuestion(nextQuestion, questionHistory))
            } else {
                // Mock HTTP request
                delay(2000)
                Result.success(HandleAnswerSelectionUseCase.Output.QuizCompleted)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}