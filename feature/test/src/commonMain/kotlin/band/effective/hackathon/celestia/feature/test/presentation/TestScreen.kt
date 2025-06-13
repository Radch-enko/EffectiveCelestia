package band.effective.hackathon.celestia.feature.test.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TestScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Test Screen", style = MaterialTheme.typography.displayLarge)
    }
}