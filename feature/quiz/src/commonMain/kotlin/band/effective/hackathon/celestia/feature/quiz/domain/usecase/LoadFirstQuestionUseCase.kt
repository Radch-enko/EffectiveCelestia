package band.effective.hackathon.celestia.feature.quiz.domain.usecase

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Use case for loading the first question of the quiz.
 */
interface LoadFirstQuestionUseCase {
    suspend operator fun invoke(): Result<Question>
}