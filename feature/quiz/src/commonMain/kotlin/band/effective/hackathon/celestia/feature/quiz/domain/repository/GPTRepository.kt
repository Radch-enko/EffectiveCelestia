package band.effective.hackathon.celestia.feature.quiz.domain.repository

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.feature.quiz.domain.model.GptError
import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet

/**
 * Repository interface for interacting with ChatGPT
 */
interface GPTRepository {
    /**
     * Generates a planet recommendation based on user answers
     *
     * @param userAnswers Map of question IDs to selected answers
     * @return Either containing the recommended planet name or an error
     */
    suspend fun generatePlanetRecommendation(userAnswers: Map<String, String>): Either<GptError, RecommendedPlanet>
}
