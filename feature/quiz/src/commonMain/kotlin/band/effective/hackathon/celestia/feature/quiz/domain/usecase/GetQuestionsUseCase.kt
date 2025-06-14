package band.effective.hackathon.celestia.feature.quiz.domain.usecase

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Use case for getting all quiz questions.
 */
interface GetQuestionsUseCase {
    suspend fun invoke(): List<Question>
}