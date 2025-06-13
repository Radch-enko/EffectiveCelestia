package band.effective.hackathon.celestia.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AnswerOption(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0x14FFFFFF),
        ),
        shape = RoundedCornerShape(18.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = CenterVertically,
        ) {
            AnswerIndicator(isSelected = isSelected)

            Spacer(Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.White,
                textAlign = TextAlign.Start,
            )
        }
    }
}

@Composable
fun AnswerIndicator(isSelected: Boolean) {
    Box(
        Modifier
            .size(20.dp)
            .border(1.dp, Color.White, CircleShape)
    ) {
        if (isSelected) {
            Box(
                Modifier
                    .size(8.dp)
                    .background(Color.White, CircleShape)
                    .align(androidx.compose.ui.Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun AnswerIndicatorPreview() {
    AnswerIndicator(isSelected = true)
}