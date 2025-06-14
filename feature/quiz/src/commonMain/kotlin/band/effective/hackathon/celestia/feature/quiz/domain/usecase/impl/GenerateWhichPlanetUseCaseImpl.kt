package band.effective.hackathon.celestia.feature.quiz.domain.usecase.impl

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.core.domain.functional.error
import band.effective.hackathon.celestia.core.domain.functional.map
import band.effective.hackathon.celestia.feature.quiz.domain.model.GptError
import band.effective.hackathon.celestia.feature.quiz.domain.repository.GPTRepository
import band.effective.hackathon.celestia.feature.quiz.domain.usecase.GenerateWhichPlanetUseCase

/**
 * Implementation of [GenerateWhichPlanetUseCase] that uses [gptRepository] to generate planet recommendations.
 *
 * @property gptRepository Repository for interacting with ChatGPT
 */
class GenerateWhichPlanetUseCaseImpl(
    private val gptRepository: GPTRepository
) : GenerateWhichPlanetUseCase {
    /**
     * Generates a planet recommendation based on user answers.
     *
     * @param params Parameters containing the user answers
     * @return Either containing the recommended planet name or an error
     */
    override suspend fun invoke(params: GenerateWhichPlanetUseCase.Params): Either<GptError, GenerateWhichPlanetUseCase.Output> {
        try {
            // Convert Answer objects to their text representation
            val answerTexts = params.userAnswers.mapValues { (_, answer) -> answer.text }

            // Call the repository to generate the planet recommendation
            return gptRepository.generatePlanetRecommendation(answerTexts)
                .map { planet ->
                    GenerateWhichPlanetUseCase.Output(planet)
                }
        } catch (e: Exception) {
            return error(
                GptError(
                    message = e.message ?: "Unknown error occurred while generating planet recommendation",
                )
            )
        }
    }
}
