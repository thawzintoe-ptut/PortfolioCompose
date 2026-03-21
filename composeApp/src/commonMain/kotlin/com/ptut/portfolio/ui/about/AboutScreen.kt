package com.ptut.portfolio.ui.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.data.model.Stat
import com.ptut.portfolio.theme.PortfolioColors
import com.ptut.portfolio.util.WindowWidthClass

@Composable
fun AboutScreen(
    portfolioData: PortfolioData?,
    windowWidthClass: WindowWidthClass,
) {
    val profile = portfolioData?.profile
    val stats = portfolioData?.stats ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 128.dp),
    ) {
        // Section label
        Text(
            text = "ABOUT",
            style = MaterialTheme.typography.labelLarge,
            color = PortfolioColors.Accent,
        )

        Spacer(Modifier.height(16.dp))

        // Editorial heading
        Text(
            text = "The Silent\nArchitect.",
            style = MaterialTheme.typography.displayLarge,
            color = PortfolioColors.HeadingText,
        )

        Spacer(Modifier.height(48.dp))

        // Bio
        Text(
            text = profile?.bio ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = PortfolioColors.BodyText,
        )

        Spacer(Modifier.height(48.dp))

        // Stats — vertical layout
        Column(verticalArrangement = Arrangement.spacedBy(48.dp)) {
            stats.forEach { stat ->
                StatItem(stat)
            }
        }

        Spacer(Modifier.height(96.dp))

        // Philosophy card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp))
                .background(PortfolioColors.Surface)
                .padding(32.dp),
        ) {
            Column {
                Text(
                    text = "PHILOSOPHY",
                    style = MaterialTheme.typography.labelMedium.copy(
                        letterSpacing = 1.1.sp,
                    ),
                    color = PortfolioColors.Accent,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "\"${profile?.philosophy ?: "Code is temporary, engineering integrity is permanent."}\"",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Normal,
                    color = PortfolioColors.AchievementText,
                )
            }
        }

        Spacer(Modifier.height(80.dp))

        // Divider
        HorizontalDivider(
            color = PortfolioColors.Divider.copy(alpha = 0.2f),
            thickness = 0.5.dp,
        )
    }
}

@Composable
private fun StatItem(stat: Stat) {
    Column {
        Text(
            text = stat.value,
            style = MaterialTheme.typography.headlineLarge,
            color = PortfolioColors.Accent,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stat.label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = PortfolioColors.BodyText,
        )
    }
}
