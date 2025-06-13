package band.effective.hackathon.celestia.core.ui.components.button

import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    currentProgress: Float,
    max: Float,
) {
    LinearProgressIndicator(
        modifier = modifier.height(12.dp),
        progress = { currentProgress / max },
        color = Color.White,
        trackColor = Color(0xFF24212D),
        gapSize = (-10).dp,
        drawStopIndicator = {},
    )
}