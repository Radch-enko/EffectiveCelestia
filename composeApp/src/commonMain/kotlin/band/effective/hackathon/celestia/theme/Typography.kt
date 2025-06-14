package band.effective.hackathon.celestia.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.unit.sp
import band.effective.hackathon.celestia.res.MuseoCyrl700
import band.effective.hackathon.celestia.res.Onest_Black
import band.effective.hackathon.celestia.res.MuseoCyrl700
import band.effective.hackathon.celestia.res.MuseoCyrl700
import band.effective.hackathon.celestia.res.MuseoSansCyrl_300
import band.effective.hackathon.celestia.res.Res

@Composable
fun createTypography() = Typography(
    displayLarge = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoCyrl700).toFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoSansCyrl_300).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoSansCyrl_300).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoSansCyrl_300).toFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoSansCyrl_300).toFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoSansCyrl_300).toFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = org.jetbrains.compose.resources.Font(Res.font.MuseoSansCyrl_300).toFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)