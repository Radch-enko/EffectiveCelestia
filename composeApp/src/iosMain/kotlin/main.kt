import androidx.compose.ui.window.ComposeUIViewController
import band.effective.hackathon.celestia.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
