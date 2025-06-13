package band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.LoadFirstQuestionUseCase

/**
 * Implementation of [LoadFirstQuestionUseCase] that uses [QuizRepository] to load the first question.
 *
 * @property quizRepository Repository for accessing quiz data
 */
class LoadFirstQuestionUseCaseImpl(
    private val quizRepository: QuizRepository
) : LoadFirstQuestionUseCase {
    /**
     * Loads the first question of the quiz.
     *
     * @return Result containing the first question or an error
     */
    override suspend fun invoke(): Result<Question> {
        return try {
            val questions = quizRepository.getQuestions()
            if (questions.isNotEmpty()) {
                Result.success(questions.first())
            } else {
                Result.failure(NoSuchElementException("No questions available"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}