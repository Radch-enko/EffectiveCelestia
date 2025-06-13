package band.effective.hackathon.celestia.feature.quiz.domain.repository

import band.effective.hackathon.celestia.feature.quiz.domain.model.Question

/**
 * Repository interface for accessing quiz data.
 */
interface QuizRepository {
    /**
     * Gets all questions for the quiz.
     *
     * @return List of questions
     */
    suspend fun getQuestions(): List<Question>
    
    /**
     * Gets a specific question by its ID.
     *
     * @param questionId The ID of the question to retrieve
     * @return The question with the specified ID, or null if not found
     */
    suspend fun getQuestionById(questionId: Int): Question?
}