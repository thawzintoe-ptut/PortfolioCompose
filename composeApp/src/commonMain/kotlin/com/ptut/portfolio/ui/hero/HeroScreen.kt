package com.ptut.portfolio.ui.hero

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.theme.PortfolioColors
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
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
    val subtitle = portfolioData?.profile?.subtitle ?: ""
    val github = portfolioData?.profile?.github ?: ""
    val linkedin = portfolioData?.profile?.linkedin ?: ""

    val fullGreeting = "Hi, I'm $name"
    var displayedText by remember { mutableStateOf("") }

    LaunchedEffect(fullGreeting) {
        displayedText = ""
        for (i in fullGreeting.indices) {
            displayedText = fullGreeting.substring(0, i + 1)
            delay(60L)
        }
    }

    val gradient = Brush.radialGradient(
        colors = listOf(PortfolioColors.Surface, PortfolioColors.Background),
        center = Offset(0f, 0f),
        radius = 1200f,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
    ) {
        // Theme toggle — top-right
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Avatar with primary border ring
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(3.dp, PortfolioColors.Primary, CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile photo",
                    modifier = Modifier.size(100.dp),
                    tint = PortfolioColors.Primary,
                )
            }

            Spacer(Modifier.height(4.dp))

            // Typewriter greeting
            AnimatedContent(
                targetState = displayedText,
                transitionSpec = { fadeIn(tween(80)) togetherWith fadeOut(tween(80)) },
            ) { text ->
                Text(
                    text = buildAnnotatedString {
                        val hiPart = "Hi, I'm "
                        if (text.startsWith(hiPart)) {
                            append(hiPart)
                            withStyle(SpanStyle(color = PortfolioColors.Primary, fontWeight = FontWeight.ExtraBold)) {
                                append(text.removePrefix(hiPart))
                            }
                        } else {
                            append(text)
                        }
                    },
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = PortfolioColors.Secondary,
                textAlign = TextAlign.Center,
            )

            if (subtitle.isNotEmpty()) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(Modifier.height(4.dp))

            // CTA buttons — FlowRow so they wrap on small screens
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    onClick = onViewProjects,
                    colors = ButtonDefaults.buttonColors(containerColor = PortfolioColors.Primary),
                ) {
                    Text("View Projects")
                }
                OutlinedButton(onClick = { /* TODO: Download Resume */ }) {
                    Text("Download Resume")
                }
            }

            // Social link chips
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (github.isNotEmpty()) {
                    SuggestionChip(
                        onClick = { uriHandler.openUri(github) },
                        label = { Text("GitHub", style = MaterialTheme.typography.labelMedium) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                    )
                }
                if (linkedin.isNotEmpty()) {
                    SuggestionChip(
                        onClick = { uriHandler.openUri(linkedin) },
                        label = { Text("LinkedIn", style = MaterialTheme.typography.labelMedium) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        ),
                    )
                }
            }
        }
    }
}
