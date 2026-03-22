package com.ptut.portfolio.ui.about

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

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
                text = "ABOUT",
                style = MaterialTheme.typography.labelLarge,
                color = PortfolioColors.Accent,
            )
        }

        Spacer(Modifier.height(16.dp))

        // Editorial heading
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 100)) + slideInVertically(tween(800, delayMillis = 100)) { it / 4 },
        ) {
            Text(
                text = "The Silent\nArchitect.",
                style = MaterialTheme.typography.displayLarge.copy(
                    letterSpacing = (-3.6).sp,
                ),
                color = PortfolioColors.HeadingText,
            )
        }

        Spacer(Modifier.height(48.dp))

        // Bio
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 250)),
        ) {
            Text(
                text = profile?.bio ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = PortfolioColors.BodyText,
            )
        }

        Spacer(Modifier.height(48.dp))

        // Stats — vertical layout
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 400)) + slideInVertically(tween(800, delayMillis = 400)) { it / 6 },
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(48.dp)) {
                stats.forEach { stat ->
                    StatItem(stat)
                }
            }
        }

        Spacer(Modifier.height(48.dp))

        // Philosophy bento card — editorial layout with left border accent
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 550)),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Philosophy text card with left border
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .background(PortfolioColors.Surface),
                ) {
                    // Left accent border
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(256.dp)
                            .background(PortfolioColors.Divider.copy(alpha = 0.1f)),
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 33.dp, vertical = 32.dp),
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Text(
                            text = "PHILOSOPHY",
                            style = MaterialTheme.typography.labelLarge.copy(
                                letterSpacing = 1.1.sp,
                                fontSize = 11.sp,
                            ),
                            color = PortfolioColors.Accent,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "\"${profile?.philosophy ?: "Code is temporary, engineering integrity is permanent."}\"",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Light,
                                lineHeight = 20.sp,
                            ),
                            color = PortfolioColors.AchievementText,
                        )
                    }
                }

                // Code pattern background — desaturated editorial element
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .background(PortfolioColors.Surface)
                        .alpha(0.4f),
                ) {
                    // Simulated code lines for editorial effect
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        val codeLines = listOf(
                            "fun buildArchitecture() {",
                            "    val layers = listOf(",
                            "        Domain, Data, Presentation",
                            "    )",
                            "    layers.forEach { layer ->",
                            "        layer.implement(",
                            "            principles = SOLID,",
                            "            pattern = CleanArch",
                            "        )",
                            "    }",
                            "}",
                            "",
                            "suspend fun deliver() {",
                            "    withContext(Quality) {",
                            "        ship(precision = true)",
                            "    }",
                            "}",
                        )
                        codeLines.forEach { line ->
                            Text(
                                text = line,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp,
                                ),
                                color = PortfolioColors.Accent.copy(alpha = 0.6f),
                            )
                        }
                    }
                }
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
