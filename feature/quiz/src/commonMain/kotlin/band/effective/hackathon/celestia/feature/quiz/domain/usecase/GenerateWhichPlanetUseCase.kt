package band.effective.hackathon.celestia.feature.quiz.domain.usecase

import band.effective.hackathon.celestia.core.domain.functional.Either
import band.effective.hackathon.celestia.feature.quiz.data.model.ChatGptError
import band.effective.hackathon.celestia.feature.quiz.domain.model.Answer
import band.effective.hackathon.celestia.feature.quiz.domain.model.RecommendedPlanet

/**
 * Use case for generating a planet recommendation based on user answers.
 */
interface GenerateWhichPlanetUseCase {

    suspend operator fun invoke(params: Params): Either<ChatGptError, Output>

    /**
     * Parameters for the GenerateWhichPlanetUseCase.
     *
     * @property userAnswers Map of question IDs to selected answers
     */
    data class Params(
        val userAnswers: Map<String, Answer>
    )

    /**
     * Output of the GenerateWhichPlanetUseCase.
     *
     * @property planetName The recommended planet name
     */
    data class Output(
        val planet: RecommendedPlanet
    )
}
