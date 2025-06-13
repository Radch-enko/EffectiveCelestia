package band.effective.hackathon.celestia.feature.quiz.data.repository

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.feature.quiz.data.datasource.RemoteDataSource
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptError
import band.effective.hackathon.celestia.feature.quiz.domain.repository.ChatGPTRepository
import io.github.aakira.napier.Napier

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
    override suspend fun generatePlanetRecommendation(userAnswers: Map<Int, String>): Either<ChatGptError, String> {
        Napier.d("ChatGPTRepositoryImpl: Generating planet recommendation")

        // Create a prompt specifically for planet recommendation
        val enhancedAnswers = userAnswers.mapValues { (questionId, answer) ->
            "Question $questionId: $answer"
        }

        // Add a specific instruction to the map
        val promptMap = enhancedAnswers.toMutableMap()
        promptMap[-1] =
            "Based on these answers, recommend a single planet name that would be most suitable for this person to visit. Only return the planet name, nothing else."

        return remoteDataSource.sendAnswersToChatGpt(promptMap)
    }
}
