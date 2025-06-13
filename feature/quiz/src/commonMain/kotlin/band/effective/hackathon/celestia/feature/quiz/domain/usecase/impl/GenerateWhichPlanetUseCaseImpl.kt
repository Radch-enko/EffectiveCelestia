package band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.error
import band.effective.hackathon.celestia.core.domain.functional.map
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptError
import band.effective.hackathon.celestia.feature.quiz.domain.repository.ChatGPTRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GenerateWhichPlanetUseCase

/**
 * Implementation of [GenerateWhichPlanetUseCase] that uses [ChatGPTRepository] to generate planet recommendations.
 *
 * @property chatGPTRepository Repository for interacting with ChatGPT
 */
class GenerateWhichPlanetUseCaseImpl(
    private val chatGPTRepository: ChatGPTRepository
) : GenerateWhichPlanetUseCase {
    /**
     * Generates a planet recommendation based on user answers.
     *
     * @param params Parameters containing the user answers
     * @return Either containing the recommended planet name or an error
     */
    override suspend fun invoke(params: GenerateWhichPlanetUseCase.Params): Either<ChatGptError, GenerateWhichPlanetUseCase.Output> {
        try {
            // Convert Answer objects to their text representation
            val answerTexts = params.userAnswers.mapValues { (_, answer) -> answer.text }

            // Call the repository to generate the planet recommendation
            return chatGPTRepository.generatePlanetRecommendation(answerTexts)
                .map { planet ->
                    GenerateWhichPlanetUseCase.Output(planet)
                }
        } catch (e: Exception) {
            return error(
                ChatGptError(
                    message = e.message ?: "Unknown error occurred while generating planet recommendation",
                    type = "exception",
                    code = e::class.simpleName ?: "Exception"
                )
            )
        }
    }
}
