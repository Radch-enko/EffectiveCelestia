import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import band.effective.hackathon.celestia.App
import band.effective.hackathon.celestia.di.initKoin

fun main() = application {
    initKoin()
    Window(
        title = "EffectiveCelestia",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App()
    }
}

