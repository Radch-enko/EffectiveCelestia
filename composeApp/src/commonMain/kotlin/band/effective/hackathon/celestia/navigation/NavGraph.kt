package band.effective.hackathon.celestia.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import band.effective.hackathon.celestia.feature.quiz.presentation.QuizScreen
import band.effective.hackathon.celestia.feature.splash.presentation.SplashScreen
import band.effective.hackathon.celestia.feature.test.presentation.TestScreen
import band.effective.hackathon.celestia.screens.AboutUsScreen

/**
 * Main navigation graph for the app
 */
@Composable
fun NavGraph(navController: NavHostController, startDestination: String = NavRoutes.SPLASH) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
    ) {
        composable(NavRoutes.SPLASH) {
            SplashScreen(onSplashCompleted = {
                navController.navigate(NavRoutes.QUIZ) {
                    popUpTo(NavRoutes.SPLASH) { inclusive = true }
                }
            })
        }

        // Quiz nested navigation
        navigation(
            startDestination = NavRoutes.Quiz.QUIZ_SCREEN,
            route = NavRoutes.Quiz.ROUTE
        ) {
            composable(NavRoutes.Quiz.QUIZ_SCREEN) {
                QuizScreen(onQuizCompleted = {
                    navController.navigate(NavRoutes.Quiz.TEST_SCREEN)
                })
            }

            composable(NavRoutes.Quiz.TEST_SCREEN) {
                TestScreen()
            }
        }

        // About Us nested navigation
        navigation(
            startDestination = NavRoutes.AboutUs.ABOUT_US_SCREEN,
            route = NavRoutes.AboutUs.ROUTE
        ) {
            composable(NavRoutes.AboutUs.ABOUT_US_SCREEN) {
                AboutUsScreen()
            }
        }
    }
}
