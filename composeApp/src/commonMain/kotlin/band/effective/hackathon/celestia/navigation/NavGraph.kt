package band.effective.hackathon.celestia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import band.effective.hackathon.celestia.feature.splash.presentation.SplashScreen
import band.effective.hackathon.celestia.feature.test.presentation.TestScreen

/**
 * Main navigation graph for the app
 */
@Composable
fun NavGraph(navController: NavHostController, startDestination: String = NavRoutes.SPLASH) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavRoutes.SPLASH) {
            SplashScreen(onSplashCompleted = {
                navController.navigate(NavRoutes.TEST) {
                    popUpTo(NavRoutes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(NavRoutes.TEST) {
            TestScreen()
        }
    }
}