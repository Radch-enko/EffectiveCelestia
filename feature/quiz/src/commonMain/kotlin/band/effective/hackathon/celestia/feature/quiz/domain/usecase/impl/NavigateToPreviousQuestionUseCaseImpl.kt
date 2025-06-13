package band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.NavigateToPreviousQuestionUseCase

/**
 * Implementation of [NavigateToPreviousQuestionUseCase] that uses [QuizRepository] to navigate to the previous question.
 *
 * @property quizRepository Repository for accessing quiz data
 */
class NavigateToPreviousQuestionUseCaseImpl(
    private val quizRepository: QuizRepository
) : NavigateToPreviousQuestionUseCase {
    /**
     * Navigates to the previous question.
     *
     * @param params The history of question IDs
     * @return Result containing the previous question and updated history, or an error
     */
    override suspend fun invoke(params: List<Int>): Result<Pair<Question, List<Int>>> {
        return try {
            // Can't go back if we're at the first question or if history is empty
            if (params.size <= 1) {
                return Result.failure(IllegalStateException("Cannot go back from the first question"))
            }

            // Create a mutable copy of the history
            val updatedHistory = params.toMutableList()

            // Remove current question from history
            updatedHistory.removeAt(updatedHistory.lastIndex)

            // Get previous question ID
            val previousQuestionId = updatedHistory.last()
            val previousQuestion = quizRepository.getQuestionById(previousQuestionId)

            if (previousQuestion != null) {
                Result.success(Pair(previousQuestion, updatedHistory))
            } else {
                Result.failure(NoSuchElementException("Previous question not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}