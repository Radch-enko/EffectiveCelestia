package band.effective.hackathon.celestia.core.ui.components.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.West
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.size(64.dp, 52.dp),
        color = Color(0x14FFE2C7),
        onClick = onClick,
        shape = RoundedCornerShape(18.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                imageVector = Icons.Outlined.West,
                contentDescription = "Back",
                tint = Color.White,
            )
        }
    }
}