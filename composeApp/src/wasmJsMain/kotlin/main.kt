import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import band.effective.hackathon.celestia.App
import band.effective.hackathon.celestia.di.initKoin
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = document.body ?: return
    initKoin()
    ComposeViewport(body) {
        App()
    }
}
