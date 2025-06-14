package band.effective.hackathon.celestia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import band.effective.hackathon.celestia.core.ui.SpaceBackground
import band.effective.hackathon.celestia.navigation.BottomNavigation
import band.effective.hackathon.celestia.navigation.NavGraph
import band.effective.hackathon.celestia.navigation.NavRoutes
import band.effective.hackathon.celestia.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun App() = AppTheme {
    val navController = rememberNavController()

    SpaceBackground {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            contentColor = androidx.compose.ui.graphics.Color.Transparent,
            bottomBar = {
                BottomNavigation(navController)
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavGraph(
                    navController = navController,
                    startDestination = NavRoutes.QUIZ
                )
            }
        }
    }
}
