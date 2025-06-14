package band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question
import band.effective.hackathon.celestia.feature.quiz.domain.repository.QuizRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GetQuestionsUseCase

/**
 * Implementation of [GetQuestionsUseCase] that uses [QuizRepository] to get all questions.
 *
 * @property quizRepository Repository for accessing quiz data
 */
class GetQuestionsUseCaseImpl(
    private val quizRepository: QuizRepository
) : GetQuestionsUseCase {
    /**
     * Gets all questions for the quiz.
     *
     * @return List of questions
     */
    override suspend fun invoke(): List<Question> {
        return quizRepository.getQuestions()
    }
}