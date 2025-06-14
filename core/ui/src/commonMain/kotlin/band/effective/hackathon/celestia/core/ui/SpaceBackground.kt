package band.effective.hackathon.celestia.core.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.random.Random
import kotlinx.coroutines.launch

@Composable
fun SpaceBackground(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D0D1A), // Почти чёрный/тёмно-синий
                        Color(0xFF3C0B28), // Тёмно-бордовый
                        Color(0xFF911E34), // Красноватый
                        Color(0xFFDE523D), // Оранжево-красный
                        Color(0xFFF9944B)  // Светло-оранжевый
                    )
                )
            )
    ) {
        StarryGradientBackground(starCount = 100)
        Box(modifier = Modifier.systemBarsPadding()) {
            content()
        }
    }
}


@Composable
fun StarryGradientBackground(starCount: Int = 50) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D0D1A),
                        Color(0xFF3C0B28),
                        Color(0xFF911E34),
                        Color(0xFFDE523D),
                        Color(0xFFF9944B)
                    )
                )
            )
    ) {
        AnimatedStarsLayer(starCount = starCount)
    }
}

@Composable
private fun AnimatedStarsLayer(starCount: Int) {
    val stars = remember {
        List(starCount) {
            AnimatedStar(
                baseX = Random.nextFloat(),
                baseY = Random.nextFloat(),
                baseRadius = Random.nextFloat() * 2f + 0.5f
            )
        }
    }

    stars.forEach { star ->
        LaunchedEffect(star.id) {
            // Падение по Y
            launch {
                while (true) {
                    val offset = Random.nextFloat() * 20f + 50f // до 30px вниз
                    val duration = Random.nextInt(4000, 8000)
                    star.offsetY.animateTo(
                        offset,
                        animationSpec = tween(durationMillis = duration, easing = LinearEasing)
                    )
                    star.offsetY.animateTo(
                        0f,
                        animationSpec = tween(durationMillis = duration, easing = LinearEasing)
                    )
                }
            }

            // Пульсация радиуса
            launch {
                while (true) {
                    val scale = Random.nextFloat() * 1.2f + 0.9f // 0.9x to 2.1x
                    val duration = Random.nextInt(2000, 4000)
                    star.radius.animateTo(
                        scale,
                        animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing)
                    )
                    star.radius.animateTo(
                        1f,
                        animationSpec = tween(durationMillis = duration, easing = FastOutSlowInEasing)
                    )
                }
            }

            // Появление (альфа)
            launch {
                star.alpha.snapTo(0f)
                star.alpha.animateTo(
                    0.9f,
                    animationSpec = tween(durationMillis = 1500)
                )
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        stars.forEach { star ->
            drawCircle(
                color = Color.White.copy(alpha = star.alpha.value),
                radius = star.baseRadius * star.radius.value,
                center = Offset(
                    x = star.baseX * size.width,
                    y = star.baseY * size.height + star.offsetY.value
                )
            )
        }
    }
}

private data class AnimatedStar(
    val id: String = Random.nextInt().toString(),
    val baseX: Float,
    val baseY: Float,
    val baseRadius: Float,
    val offsetY: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val radius: Animatable<Float, AnimationVector1D> = Animatable(1f),
    val alpha: Animatable<Float, AnimationVector1D> = Animatable(0f)
)

