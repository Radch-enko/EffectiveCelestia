package band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GetQuestionByIdUseCase

/**
 * Implementation of [GetQuestionByIdUseCase] that uses [QuizRepository] to get a specific question by its ID.
 *
 * @property quizRepository Repository for accessing quiz data
 */
class GetQuestionByIdUseCaseImpl(
    private val quizRepository: QuizRepository
) : GetQuestionByIdUseCase {
    /**
     * Gets a specific question by its ID.
     *
     * @param params The ID of the question to retrieve
     * @return The question with the specified ID, or null if not found
     */
    override suspend fun invoke(params: Int): Question? {
        return quizRepository.getQuestionById(params)
    }
}