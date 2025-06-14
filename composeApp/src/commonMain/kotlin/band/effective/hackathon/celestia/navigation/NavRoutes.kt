package band.effective.hackathon.celestia.navigation

import kotlinx.serialization.Serializable

/**
 * Navigation routes for the app
 */
object NavRoutes {
    const val SPLASH = "splash"
    const val TEST = "test"

    // Root destinations
    const val QUIZ = "quiz"
    const val ABOUT_US = "about_us"

    // Nested routes
    object Quiz {
        const val ROUTE = QUIZ
        const val QUIZ_SCREEN = "$ROUTE/quiz_screen"
        const val TEST_SCREEN = "$ROUTE/test_screen"

        @Serializable
        data class Planet(
            val planetName: String,
            val description: String,
            val type: Int,
        )
    }

    object AboutUs {
        const val ROUTE = ABOUT_US
        const val ABOUT_US_SCREEN = "$ROUTE/about_us_screen"
    }
}
