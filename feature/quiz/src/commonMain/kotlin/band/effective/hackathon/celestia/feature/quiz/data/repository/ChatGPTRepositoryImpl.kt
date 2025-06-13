package band.effective.hackathon.celestia.feature.quiz.data.repository

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.map
import band.effective.hackathon.celestia.feature.quiz.data.datasource.RemoteDataSource
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptError
import band.effective.hackathon.celestia.feature.quiz.data.model.RecommendedPlanetDto
import band.effective.hackathon.celestia.feature.quiz.domain.model.RecommendedPlanet
import band.effective.hackathon.celestia.feature.quiz.domain.repository.ChatGPTRepository

/**
 * Implementation of ChatGPTRepository that uses RemoteDataSource
 */
class ChatGPTRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : ChatGPTRepository {

    /**
     * Generates a planet recommendation based on user answers
     *
     * @param userAnswers Map of question IDs to selected answers
     * @return Either containing the recommended planet name or an error
     */
    override suspend fun generatePlanetRecommendation(userAnswers: Map<String, String>): Either<ChatGptError, RecommendedPlanet> {
        return remoteDataSource.sendAnswersToChatGpt(userAnswers).map {
            RecommendedPlanet(
                planetName = it.planetName,
                description = it.description,
                type = it.type
            )
        }
    }
}
