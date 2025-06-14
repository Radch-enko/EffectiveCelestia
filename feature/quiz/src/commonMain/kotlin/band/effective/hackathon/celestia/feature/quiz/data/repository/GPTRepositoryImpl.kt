package band.effective.hackathon.celestia.feature.quiz.data.repository

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.map
import band.effective.hackathon.celestia.core.domain.functional.mapError
import band.effective.hackathon.celestia.feature.quiz.data.datasource.RemoteDataSource
import band.effective.hackathon.celestia.feature.quiz.data.mapper.GptDomainMapper
import band.effective.hackathon.celestia.feature.quiz.domain.model.GptError
import band.effective.hackathon.celestia.core.domain.model.RecommendedPlanet
import band.effective.hackathon.celestia.feature.quiz.domain.repository.GPTRepository

/**
 * Implementation of ChatGPTRepository that uses RemoteDataSource
 */
class GPTRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val gptDomainMapper: GptDomainMapper,
) : GPTRepository {

    /**
     * Generates a planet recommendation based on user answers
     *
     * @param userAnswers Map of question IDs to selected answers
     * @return Either containing the recommended planet name or an error
     */
    override suspend fun generatePlanetRecommendation(userAnswers: Map<String, String>): Either<GptError, RecommendedPlanet> {
        return remoteDataSource.sendAnswersToChatGpt(userAnswers)
            .mapError(gptDomainMapper::map)
            .map(gptDomainMapper::map)
    }
}
