import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import band.effective.hackathon.celestia.App
import band.effective.hackathon.celestia.di.initKoin
import java.awt.Dimension

fun main() = application {
    initKoin()
    Window(
        title = "EffectiveCelestia",
        state = rememberWindowState(width = 300.dp, height = 800.dp),
        resizable = false,
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App()
    }
}

