package band.effective.hackathon.celestia.feature.quiz.domain.usecase

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Use case for navigating to the previous question.
 */
interface NavigateToPreviousQuestionUseCase {
    suspend operator fun invoke(params: List<Int>): Result<Pair<Question, List<Int>>>
}