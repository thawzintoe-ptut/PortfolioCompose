package com.ptut.portfolio.ui.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp)
            .padding(top = 128.dp, bottom = 160.dp),
    ) {
        // Section label
        Text(
            text = "PROJECTS",
            style = MaterialTheme.typography.labelLarge,
            color = PortfolioColors.Accent,
        )

        Spacer(Modifier.height(16.dp))

        // Editorial heading with italic "precision"
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

        Spacer(Modifier.height(64.dp))

        // Project list
        Column(verticalArrangement = Arrangement.spacedBy(96.dp)) {
            projects.forEach { project ->
                ProjectArticle(project = project)
            }
        }
    }
}

@Composable
private fun ProjectArticle(project: Project) {
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

            // Tags — monospace uppercase
            if (project.tags.isNotEmpty()) {
                Row(
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

            // Source link
            Text(
                text = "SOURCE →",
                style = MaterialTheme.typography.labelMedium.copy(
                    letterSpacing = 1.2.sp,
                ),
                color = PortfolioColors.Accent,
                modifier = Modifier.padding(top = 12.dp),
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
