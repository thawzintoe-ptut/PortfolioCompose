package com.ptut.portfolio.ui.hero

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.theme.PortfolioColors

@Composable
fun HeroScreen(
    portfolioData: PortfolioData?,
    onViewProjects: () -> Unit,
    onToggleTheme: () -> Unit,
    isDark: Boolean = true,
) {
    val profile = portfolioData?.profile
    val name = profile?.name ?: "Your Name"
    val title = profile?.title ?: "Senior Software Engineer"
    val subtitle = profile?.subtitle ?: ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.Background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 40.dp)
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(Modifier.weight(1f))

            // Large editorial name
            Text(
                text = name.replace(" ", "\n"),
                style = MaterialTheme.typography.displayLarge,
                color = PortfolioColors.HeadingText,
            )

            Spacer(Modifier.height(24.dp))

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = PortfolioColors.BodyText,
            )

            Spacer(Modifier.height(8.dp))

            // Subtitle — uppercase meta
            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = PortfolioColors.MutedText,
                )
            }

            Spacer(Modifier.height(64.dp))

            // CTA links — editorial underlined style
            Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                Text(
                    text = "View Projects →",
                    style = MaterialTheme.typography.bodySmall,
                    color = PortfolioColors.Accent,
                    textDecoration = TextDecoration.Underline,
                )
                Text(
                    text = "Download Resume ↓",
                    style = MaterialTheme.typography.bodySmall,
                    color = PortfolioColors.BodyText,
                    textDecoration = TextDecoration.Underline,
                )
            }

            Spacer(Modifier.weight(1f))

            // Footer — social links
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 80.dp),
            ) {
                Text(
                    text = "github",
                    style = MaterialTheme.typography.labelMedium.copy(
                        letterSpacing = 1.2.sp,
                    ),
                    color = PortfolioColors.MutedText,
                )
                Text(
                    text = "·",
                    style = MaterialTheme.typography.labelMedium,
                    color = PortfolioColors.MutedText,
                )
                Text(
                    text = "linkedin",
                    style = MaterialTheme.typography.labelMedium.copy(
                        letterSpacing = 1.2.sp,
                    ),
                    color = PortfolioColors.MutedText,
                )
            }
        }

        // Top bar
        TopBar(
            name = name,
            onToggleTheme = onToggleTheme,
            isDark = isDark,
        )
    }
}

@Composable
private fun TopBar(
    name: String,
    onToggleTheme: () -> Unit,
    isDark: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name.uppercase(),
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = 4.sp,
                fontSize = 12.sp,
            ),
            color = PortfolioColors.Accent,
        )
        Text(
            text = if (isDark) "☾" else "☀",
            style = MaterialTheme.typography.bodySmall,
            color = PortfolioColors.HeadingText,
            modifier = Modifier
                .padding(4.dp),
        )
    }
}
