package band.effective.hackathon.celestia.feature.aboutus.presentation

/**
 * Represents UI effects for the About Us feature.
 */
sealed interface AboutUsEffect {
    /**
     * Indicates that the user wants to vote.
     */
    object Vote : AboutUsEffect
}