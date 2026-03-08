package com.ptut.portfolio.ui.hero

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.theme.PortfolioColors
import kotlinx.coroutines.delay

@Composable
fun HeroScreen(
    portfolioData: PortfolioData?,
    onViewProjects: () -> Unit,
    onToggleTheme: () -> Unit,
    isDark: Boolean = true,
) {
    val uriHandler = LocalUriHandler.current
    val name = portfolioData?.profile?.name ?: "Your Name"
    val title = portfolioData?.profile?.title ?: "Senior Software Engineer"
    val github = portfolioData?.profile?.github ?: ""
    val linkedin = portfolioData?.profile?.linkedin ?: ""

    // Typewriter animation state
    val fullGreeting = "Hi, I'm $name"
    var displayedText by remember { mutableStateOf("") }
    var charIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(fullGreeting) {
        displayedText = ""
        charIndex = 0
        for (i in fullGreeting.indices) {
            displayedText = fullGreeting.substring(0, i + 1)
            delay(60L)
        }
    }

    val gradient = Brush.radialGradient(
        colors = listOf(
            PortfolioColors.Surface,
            PortfolioColors.Background,
        ),
        center = Offset(0f, 0f),
        radius = 1200f,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
    ) {
        // Theme toggle button top-right
        IconButton(
            onClick = onToggleTheme,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp),
        ) {
            Icon(
                imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                contentDescription = "Toggle theme",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // Profile photo placeholder
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile photo",
                modifier = Modifier.size(120.dp),
                tint = PortfolioColors.Primary,
            )

            Spacer(Modifier.height(8.dp))

            // Typewriter greeting
            AnimatedContent(
                targetState = displayedText,
                transitionSpec = {
                    fadeIn(tween(100)) togetherWith fadeOut(tween(100))
                },
            ) { text ->
                Text(
                    text = buildAnnotatedString {
                        val hiPart = "Hi, I'm "
                        if (text.startsWith(hiPart)) {
                            append(hiPart)
                            withStyle(SpanStyle(color = PortfolioColors.Primary, fontWeight = FontWeight.Bold)) {
                                append(text.removePrefix(hiPart))
                            }
                        } else {
                            append(text)
                        }
                    },
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = PortfolioColors.Secondary,
            )

            Spacer(Modifier.height(8.dp))

            // CTA buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onViewProjects,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PortfolioColors.Primary,
                    ),
                ) {
                    Text("View Projects")
                }
                OutlinedButton(
                    onClick = { /* TODO: Download Resume */ },
                ) {
                    Text("Download Resume")
                }
            }

            Spacer(Modifier.height(8.dp))

            // Social links
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (github.isNotEmpty()) {
                    IconButton(onClick = { uriHandler.openUri(github) }) {
                        Text(
                            text = "GH",
                            style = MaterialTheme.typography.labelMedium,
                            color = PortfolioColors.Primary,
                        )
                    }
                }
                if (linkedin.isNotEmpty()) {
                    IconButton(onClick = { uriHandler.openUri(linkedin) }) {
                        Text(
                            text = "LI",
                            style = MaterialTheme.typography.labelMedium,
                            color = PortfolioColors.Primary,
                        )
                    }
                }
            }
        }
    }
}
