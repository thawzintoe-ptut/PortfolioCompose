package com.ptut.portfolio.ui.experience

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ptut.portfolio.data.model.Experience
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.theme.PortfolioColors
import com.ptut.portfolio.util.WindowWidthClass

@Composable
fun ExperienceScreen(
    portfolioData: PortfolioData?,
    windowWidthClass: WindowWidthClass,
) {
    val experiences = portfolioData?.experiences ?: emptyList()

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.BackgroundAlt)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 128.dp),
    ) {
        // Section header
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(600)),
        ) {
            Text(
                text = "EXPERIENCE",
                style = MaterialTheme.typography.labelLarge,
                color = PortfolioColors.Accent,
            )
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 100)) + slideInVertically(tween(800, delayMillis = 100)) { it / 4 },
        ) {
            Text(
                text = "The Silent\nJourney.",
                style = MaterialTheme.typography.displayMedium,
                color = PortfolioColors.HeadingText,
            )
        }

        Spacer(Modifier.height(80.dp))

        // Timeline with staggered animations
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            experiences.forEachIndexed { index, experience ->
                val opacity = when (index) {
                    0 -> 1f
                    1 -> 0.8f
                    2 -> 0.6f
                    3 -> 0.4f
                    4 -> 0.3f
                    5 -> 0.2f
                    else -> 0.1f
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(600, delayMillis = 300 + (index * 80))) +
                            slideInVertically(tween(600, delayMillis = 300 + (index * 80))) { it / 6 },
                ) {
                    Column {
                        TimelineItem(
                            experience = experience,
                            opacity = opacity,
                            isFirst = index == 0,
                            isLast = index == experiences.lastIndex,
                        )
                        if (index < experiences.lastIndex) {
                            Spacer(Modifier.height(80.dp))
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(80.dp))

        // Philosophy quote at bottom
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 800)),
        ) {
            Column {
                HorizontalDivider(
                    color = PortfolioColors.Divider.copy(alpha = 0.1f),
                    thickness = 1.dp,
                )

                Spacer(Modifier.height(41.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = "PHILOSOPHY",
                        style = MaterialTheme.typography.labelLarge,
                        color = PortfolioColors.Accent,
                        textAlign = TextAlign.End,
                    )
                    Spacer(Modifier.height(14.75.dp))
                    Text(
                        text = "Architecture is the learned game, correct and\nmagnificent, of forms assembled in the light.\nCode is no different.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PortfolioColors.MetaText,
                        textAlign = TextAlign.End,
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineItem(
    experience: Experience,
    opacity: Float,
    isFirst: Boolean,
    isLast: Boolean,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // Timeline dot
        Box(
            modifier = Modifier.width(40.dp),
            contentAlignment = Alignment.TopStart,
        ) {
            if (isFirst) {
                // Active dot — glowing green
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .offset(y = 8.dp)
                        .clip(CircleShape)
                        .background(PortfolioColors.Accent),
                )
            } else {
                // Inactive dot — gray
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .offset(y = 8.dp)
                        .clip(CircleShape)
                        .background(PortfolioColors.Divider.copy(alpha = 0.6f)),
                )
            }
        }

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .alpha(opacity),
        ) {
            // Role title
            Text(
                text = "${experience.company} - ${experience.role}",
                style = MaterialTheme.typography.headlineSmall,
                color = PortfolioColors.HeadingText,
            )

            Spacer(Modifier.height(4.dp))

            // Duration and location
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = experience.duration.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = PortfolioColors.MetaText,
                )
                if (experience.location.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(PortfolioColors.MetaText.copy(alpha = 0.3f)),
                    )
                }
            }

            if (experience.location.isNotEmpty()) {
                Text(
                    text = experience.location.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = PortfolioColors.MetaText,
                )
            }

            // Achievements — dot separated editorial list
            if (experience.achievements.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))
                Column(verticalArrangement = Arrangement.spacedBy(11.75.dp)) {
                    experience.achievements.forEach { achievement ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = achievement,
                                style = MaterialTheme.typography.bodyMedium,
                                color = PortfolioColors.AchievementText,
                            )
                            Text(
                                text = "•",
                                style = MaterialTheme.typography.bodyMedium,
                                color = PortfolioColors.Accent,
                            )
                        }
                    }
                }
            }
        }
    }
}
