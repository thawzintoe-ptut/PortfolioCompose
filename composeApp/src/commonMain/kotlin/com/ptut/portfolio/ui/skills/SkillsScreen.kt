package com.ptut.portfolio.ui.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.data.model.Skill
import com.ptut.portfolio.theme.PortfolioColors
import com.ptut.portfolio.util.WindowWidthClass

@Composable
fun SkillsScreen(
    portfolioData: PortfolioData?,
    windowWidthClass: WindowWidthClass,
) {
    val skills = portfolioData?.skills ?: emptyList()
    val grouped = skills.groupBy { it.category }
    val columns = if (windowWidthClass == WindowWidthClass.Expanded) 3 else 2

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        item {
            Text(
                text = "Skills",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        grouped.forEach { (category, skillsInCategory) ->
            item(key = category) {
                SkillCategorySection(
                    category = category,
                    skills = skillsInCategory,
                    columns = columns,
                )
            }
        }

        if (skills.isEmpty()) {
            item {
                Text(
                    text = "Add your skills to portfolio_data.json",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun SkillCategorySection(
    category: String,
    skills: List<Skill>,
    columns: Int,
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = category,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = PortfolioColors.Primary,
        )
        skills.chunked(columns).forEach { rowSkills ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                rowSkills.forEach { skill ->
                    SkillChip(skill = skill, modifier = Modifier.weight(1f))
                }
                // Fill empty trailing slots so the row stays aligned
                repeat(columns - rowSkills.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun SkillChip(
    skill: Skill,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = skill.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
