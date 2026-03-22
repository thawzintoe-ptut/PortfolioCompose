package com.ptut.portfolio.ui.skills

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.theme.PortfolioColors
import com.ptut.portfolio.util.WindowWidthClass

@Composable
fun SkillsScreen(
    portfolioData: PortfolioData?,
    windowWidthClass: WindowWidthClass,
) {
    val skills = portfolioData?.skills ?: emptyList()
    val grouped = skills.groupBy { it.category }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    // Maintain Figma category order
    val categoryOrder = listOf(
        "Language", "UI", "Architecture", "Platform",
        "Async", "DI", "Database", "Network", "DevOps", "Integrations",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 128.dp),
    ) {
        // Section label
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(600)),
        ) {
            Text(
                text = "SKILLS",
                style = MaterialTheme.typography.labelLarge,
                color = PortfolioColors.Accent,
            )
        }

        Spacer(Modifier.height(8.dp))

        // Editorial heading
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 100)) + slideInVertically(tween(800, delayMillis = 100)) { it / 4 },
        ) {
            Text(
                text = "The\nStack.",
                style = MaterialTheme.typography.displayLarge,
                color = PortfolioColors.HeadingText,
            )
        }

        Spacer(Modifier.height(63.dp))

        // Skill categories — editorial list with staggered animation
        Column(verticalArrangement = Arrangement.spacedBy(55.dp)) {
            categoryOrder.forEachIndexed { index, category ->
                val skillsInCategory = grouped[category] ?: return@forEachIndexed
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(600, delayMillis = 200 + (index * 60))) +
                            slideInVertically(tween(600, delayMillis = 200 + (index * 60))) { it / 6 },
                ) {
                    Column {
                        SkillCategorySection(
                            category = category,
                            skillNames = skillsInCategory.map { it.name },
                        )
                        if (index < categoryOrder.lastIndex) {
                            Spacer(Modifier.height(0.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillCategorySection(
    category: String,
    skillNames: List<String>,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Category heading
        Text(
            text = category,
            style = MaterialTheme.typography.titleMedium,
            color = PortfolioColors.HeadingText,
        )

        // Skills as bullet-separated monospace text
        Text(
            text = skillNames.joinToString(" • "),
            style = MaterialTheme.typography.labelSmall,
            color = PortfolioColors.BodyText,
        )
    }
}
