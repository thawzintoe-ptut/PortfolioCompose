package com.ptut.portfolio.ui.projects

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.data.model.Project
import com.ptut.portfolio.theme.PortfolioColors
import com.ptut.portfolio.util.WindowWidthClass

@Composable
fun ProjectsScreen(
    portfolioData: PortfolioData?,
    windowWidthClass: WindowWidthClass,
) {
    val projects = portfolioData?.projects ?: emptyList()
    val uriHandler = LocalUriHandler.current

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp)
            .padding(top = 128.dp, bottom = 160.dp),
    ) {
        // Section label
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(600)),
        ) {
            Text(
                text = "PROJECTS",
                style = MaterialTheme.typography.labelLarge,
                color = PortfolioColors.Accent,
            )
        }

        Spacer(Modifier.height(16.dp))

        // Editorial heading with italic "precision"
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 100)) + slideInVertically(tween(800, delayMillis = 100)) { it / 4 },
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Engineering\ndigital\nexperiences\nthrough\n")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic, fontFamily = FontFamily.Serif)) {
                        append("precision")
                    }
                    append(".")
                },
                style = MaterialTheme.typography.displayMedium,
                color = PortfolioColors.HeadingText,
            )
        }

        Spacer(Modifier.height(64.dp))

        // Project list with staggered animation
        Column(verticalArrangement = Arrangement.spacedBy(96.dp)) {
            projects.forEachIndexed { index, project ->
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(600, delayMillis = 300 + (index * 100))) +
                            slideInVertically(tween(600, delayMillis = 300 + (index * 100))) { it / 6 },
                ) {
                    ProjectArticle(
                        project = project,
                        onSourceClick = {
                            if (project.githubUrl.isNotEmpty()) {
                                uriHandler.openUri(project.githubUrl)
                            }
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectArticle(
    project: Project,
    onSourceClick: () -> Unit = {},
) {
    Column {
        // Project content
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // Title
            Text(
                text = project.title,
                style = MaterialTheme.typography.headlineMedium,
                color = PortfolioColors.HeadingText,
            )

            // Description — 2 lines max
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyLarge,
                color = PortfolioColors.BodyText,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            // Tags — monospace uppercase with FlowRow for wrapping
            if (project.tags.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(top = 4.dp),
                ) {
                    project.tags.forEach { tag ->
                        Text(
                            text = tag.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                letterSpacing = 0.6.sp,
                            ),
                            color = PortfolioColors.MutedText,
                        )
                    }
                }
            }

            // Source link — clickable
            Text(
                text = "SOURCE →",
                style = MaterialTheme.typography.labelMedium.copy(
                    letterSpacing = 1.2.sp,
                ),
                color = PortfolioColors.Accent,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { onSourceClick() },
            )
        }

        Spacer(Modifier.height(64.dp))

        // Divider
        HorizontalDivider(
            color = PortfolioColors.MutedText.copy(alpha = 0.08f),
            thickness = 0.5.dp,
        )
    }
}
