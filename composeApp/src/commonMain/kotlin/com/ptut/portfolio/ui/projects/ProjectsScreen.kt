package com.ptut.portfolio.ui.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Text(
                text = "Projects",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        if (windowWidthClass == WindowWidthClass.Expanded) {
            items(projects.chunked(2), key = { it.first().id }) { rowProjects ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    rowProjects.forEach { project ->
                        ProjectCard(project = project, modifier = Modifier.weight(1f))
                    }
                    if (rowProjects.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        } else {
            items(projects, key = { it.id }) { project ->
                ProjectCard(project = project, modifier = Modifier.fillMaxWidth())
            }
        }

        if (projects.isEmpty()) {
            item {
                Text(
                    text = "Add your projects to portfolio_data.json",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProjectCard(
    project: Project,
    modifier: Modifier = Modifier,
) {
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = project.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if (project.tags.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    project.tags.forEach { tag ->
                        SuggestionChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = PortfolioColors.Primary,
                            ),
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (project.githubUrl.isNotEmpty()) {
                    TextButton(
                        onClick = { uriHandler.openUri(project.githubUrl) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = PortfolioColors.Primary,
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("GitHub")
                    }
                }
                if (project.liveUrl.isNotEmpty()) {
                    TextButton(
                        onClick = { uriHandler.openUri(project.liveUrl) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = PortfolioColors.Secondary,
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("Live")
                    }
                }
            }
        }
    }
}
