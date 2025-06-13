package band.effective.hackathon.celestia.feature.quiz.domain.usecase

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Use case for getting a specific quiz question by its ID.
 */
interface GetQuestionByIdUseCase {
    suspend fun invoke(params: Int): Question?
}