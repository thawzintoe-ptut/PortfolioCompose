package com.ptut.portfolio.ui.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortfolioColors.BackgroundAlt)
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        // Section label
        Text(
            text = "CONTACT",
            style = MaterialTheme.typography.labelLarge,
            color = PortfolioColors.Accent,
        )

        Spacer(Modifier.height(16.dp))

        // Heading
        Text(
            text = "Let's connect.",
            style = MaterialTheme.typography.headlineLarge,
            color = PortfolioColors.HeadingText,
        )

        Spacer(Modifier.height(80.dp))

        // Contact links — editorial style
        Column(verticalArrangement = Arrangement.spacedBy(56.dp)) {
            // Email
            ContactLink(
                label = "Email",
                value = "${profile?.email ?: "email@example.com"} →",
            )

            // GitHub
            ContactLink(
                label = "GitHub",
                value = "thawzintoe-ptut →",
            )

            // LinkedIn
            ContactLink(
                label = "LinkedIn",
                value = "thaw-zin-toe →",
            )
        }
    }
}

@Composable
private fun ContactLink(
    label: String,
    value: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(7.75.dp)) {
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
