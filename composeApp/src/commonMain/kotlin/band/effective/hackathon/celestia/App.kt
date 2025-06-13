package band.effective.hackathon.celestia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import band.effective.hackathon.celestia.core.ui.SpaceBackground
import band.effective.hackathon.celestia.navigation.NavGraph
import band.effective.hackathon.celestia.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun App() = AppTheme {
    AppNavigation()
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    SpaceBackground {
        NavGraph(navController = navController)
    }
}
