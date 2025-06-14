package band.effective.hackathon.celestia.navigation

import band.effective.hackathon.celestia.res.Res
import band.effective.hackathon.celestia.res.ic_about_us
import band.effective.hackathon.celestia.res.ic_quiz

/**
 * Bottom navigation items for the app
 */
object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            route = NavRoutes.Quiz.QUIZ_SCREEN,
            title = "Quiz",
            icon = Res.drawable.ic_quiz,
            contentDescription = "Quiz",
            associatedRoutes = listOf(
                NavRoutes.Quiz.PLANET_SCREEN,
            )
        ),
        BottomNavItem(
            route = NavRoutes.AboutUs.ABOUT_US_SCREEN,
            title = "About Us",
            icon = Res.drawable.ic_about_us,
            contentDescription = "About Us"
        )
    )
}
