package com.ptut.portfolio.ui.contact

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.theme.PortfolioColors
import com.ptut.portfolio.util.WindowWidthClass

@Composable
fun ContactScreen(
    portfolioData: PortfolioData?,
    windowWidthClass: WindowWidthClass,
) {
    val profile = portfolioData?.profile
    val uriHandler = LocalUriHandler.current

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.BackgroundAlt)
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        // Section label
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(600)),
        ) {
            Text(
                text = "CONTACT",
                style = MaterialTheme.typography.labelLarge,
                color = PortfolioColors.Accent,
            )
        }

        Spacer(Modifier.height(16.dp))

        // Heading
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 100)) + slideInVertically(tween(800, delayMillis = 100)) { it / 4 },
        ) {
            Text(
                text = "Let's connect.",
                style = MaterialTheme.typography.headlineLarge,
                color = PortfolioColors.HeadingText,
            )
        }

        Spacer(Modifier.height(80.dp))

        // Contact links — editorial style
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(tween(800, delayMillis = 300)) + slideInVertically(tween(800, delayMillis = 300)) { it / 6 },
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(56.dp)) {
                // Email
                ContactLink(
                    label = "Email",
                    value = "${profile?.email ?: "email@example.com"} →",
                    onClick = {
                        profile?.let { uriHandler.openUri("mailto:${it.email}") }
                    },
                )

                // GitHub
                ContactLink(
                    label = "GitHub",
                    value = "${profile?.github ?: "thawzintoe-ptut"} →",
                    onClick = {
                        profile?.let { uriHandler.openUri("https://github.com/${it.github}") }
                    },
                )

                // LinkedIn
                ContactLink(
                    label = "LinkedIn",
                    value = "${profile?.linkedin ?: "thaw-zin-toe"} →",
                    onClick = {
                        profile?.let { uriHandler.openUri("https://linkedin.com/in/${it.linkedin}") }
                    },
                )
            }
        }
    }
}

@Composable
private fun ContactLink(
    label: String,
    value: String,
    onClick: () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(7.75.dp),
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
        ) { onClick() },
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = PortfolioColors.MetaText,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = PortfolioColors.Accent,
        )
    }
}
