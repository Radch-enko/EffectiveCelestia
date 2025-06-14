package band.effective.hackathon.celestia.feature.planet.presentation

/**
 * Represents UI effects for the Planet feature.
 */
sealed interface PlanetEffect {
    /**
     * Indicates that the user wants to restart the quiz.
     */
    object RestartQuiz : PlanetEffect
}