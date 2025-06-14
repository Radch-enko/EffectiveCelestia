package band.effective.hackathon.celestia.feature.aboutus.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import band.effective.hackathon.celestia.core.ui.components.button.CelestiaButton
import band.effective.hackathon.celestia.res.MuseoSansCyrl_700
import effectivecelestia.feature.aboutus.generated.resources.Res
import effectivecelestia.feature.aboutus.generated.resources.about_us_aleksandra
import effectivecelestia.feature.aboutus.generated.resources.about_us_stas
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Composable function that displays the About Us screen.
 *
 * @param onVote Callback to be invoked when the user clicks the vote button
 */
@Composable
fun AboutUsScreen(
    onVote: () -> Unit
) {
    val viewModel = koinViewModel<AboutUsViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AboutUsEffect.Vote -> onVote()
            }
        }
    }

    AboutUsScreenContent(
        state = state,
        onVoteClick = viewModel::onVoteClick
    )
}


@Composable
internal fun AboutUsScreenContent(
    state: AboutUsState,
    onVoteClick: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.Transparent
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else if (state.error != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "О нас",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 32.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Description()

                Spacer(modifier = Modifier.height(24.dp))

                // Team member cards
                TeamMemberCard(
                    name = "Станислав Радченко",
                    role = "Software Engineer",
                    telegramTag = "@StanislavRadchenko",
                    image = Res.drawable.about_us_stas,
                )

                TeamMemberCard(
                    name = "Александра Корытова",
                    role = "UI/UX Designer",
                    telegramTag = "@alesrkt",
                    image = Res.drawable.about_us_aleksandra,
                )

                Spacer(modifier = Modifier.weight(1f))

                // Vote button
                CelestiaButton(
                    text = "Vote for us",
                    onClick = onVoteClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                )
            }
        }
    }
}

@Composable
private fun TeamMemberCard(
    name: String,
    role: String,
    telegramTag: String,
    image: DrawableResource,
) {
    Row(verticalAlignment = CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Image(
            modifier = Modifier.size(150.dp),
            painter = painterResource(image),
            contentDescription = null,
        )

        Spacer(Modifier.width(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = telegramTag,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}

@Composable
fun Description() {
    val text =
        "*Effective Celestia* — это хакатонный проект, в котором мы соединили технологии и немного космической поэзии.\n" +
                "Мы сделали это, чтобы напомнить: *каждый сияет по-своему*."

    Text(
        text = buildAnnotatedString {
            val regex = Regex("\\*(.*?)\\*")
            var currentIndex = 0

            for (match in regex.findAll(text)) {
                val range = match.range
                val before = text.substring(currentIndex, range.first)
                val boldText = match.groupValues[1]

                append(before)
                withStyle(
                    SpanStyle(
                        fontFamily = FontFamily(
                            Font(band.effective.hackathon.celestia.res.Res.font.MuseoSansCyrl_700)
                        ), fontWeight = FontWeight.Bold
                    )
                ) {
                    append(boldText)
                }
                currentIndex = range.last + 1
            }
            if (currentIndex < text.length) {
                append(text.substring(currentIndex))
            }
        },
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White
    )
}

@Preview
@Composable
fun AboutUsScreenContentPreview() {
    MaterialTheme {
        AboutUsScreenContent(
            state = AboutUsState(),
            onVoteClick = {}
        )
    }
}

@Preview
@Composable
fun AboutUsScreenContentLoadingPreview() {
    MaterialTheme {
        AboutUsScreenContent(
            state = AboutUsState(isLoading = true),
            onVoteClick = {}
        )
    }
}

@Preview
@Composable
fun AboutUsScreenContentErrorPreview() {
    MaterialTheme {
        AboutUsScreenContent(
            state = AboutUsState(error = "An error occurred"),
            onVoteClick = {}
        )
    }
}