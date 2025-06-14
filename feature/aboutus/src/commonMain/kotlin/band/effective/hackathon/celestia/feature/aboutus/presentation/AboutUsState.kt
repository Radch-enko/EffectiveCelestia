package band.effective.hackathon.celestia.feature.aboutus.presentation

/**
 * Represents the state of the About Us UI.
 *
 * @property isLoading Whether the screen is currently loading
 * @property error Error message to display, or null if there is no error
 */
data class AboutUsState(
    val isLoading: Boolean = false,
    val error: String? = null
)