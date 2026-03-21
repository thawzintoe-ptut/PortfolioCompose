package com.ptut.portfolio.ui.skills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        Text(
            text = "SKILLS",
            style = MaterialTheme.typography.labelLarge,
            color = PortfolioColors.Accent,
        )

        Spacer(Modifier.height(8.dp))

        // Editorial heading
        Text(
            text = "The\nStack.",
            style = MaterialTheme.typography.displayLarge,
            color = PortfolioColors.HeadingText,
        )

        Spacer(Modifier.height(63.dp))

        // Skill categories — editorial list
        Column(verticalArrangement = Arrangement.spacedBy(55.dp)) {
            categoryOrder.forEach { category ->
                val skillsInCategory = grouped[category] ?: return@forEach
                SkillCategorySection(
                    category = category,
                    skillNames = skillsInCategory.map { it.name },
                )
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
