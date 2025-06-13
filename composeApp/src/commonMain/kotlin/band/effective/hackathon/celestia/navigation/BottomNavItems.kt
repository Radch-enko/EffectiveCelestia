package band.effective.hackathon.celestia.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import band.effective.hackathon.celestia.res.Res
import band.effective.hackathon.celestia.res.ic_about_us
import band.effective.hackathon.celestia.res.ic_quiz

/**
 * Bottom navigation items for the app
 */
object BottomNavItems {
    val items = listOf(
        BottomNavItem(
            route = NavRoutes.QUIZ,
            title = "Quiz",
            icon = Res.drawable.ic_quiz,
            contentDescription = "Quiz"
        ),
        BottomNavItem(
            route = NavRoutes.ABOUT_US,
            title = "About Us",
            icon = Res.drawable.ic_about_us,
            contentDescription = "About Us"
        )
    )
}
