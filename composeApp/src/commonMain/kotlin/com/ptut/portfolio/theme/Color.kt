package com.ptut.portfolio.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object PortfolioColors {
    // Figma editorial palette
    val Accent = Color(0xFF6EE7B7)        // Emerald/mint green — primary accent
    val Background = Color(0xFF0A0A0A)     // Near-black background
    val BackgroundAlt = Color(0xFF131313)  // Slightly lighter bg (Experience, Contact)
    val Surface = Color(0xFF1C1B1B)        // Card surfaces
    val HeadingText = Color(0xFFEDEDED)    // Primary headings
    val BodyText = Color(0xFF737373)       // Body copy
    val MutedText = Color(0xFF404040)      // Tags, subtle labels
    val MetaText = Color(0xFF919191)       // Duration, meta info
    val AchievementText = Color(0xFFC6C6C6) // Achievement items
    val Divider = Color(0xFF474747)        // Dividers, timeline

    // Light mode
    val BackgroundLight = Color(0xFFF8F8FF)
    val SurfaceLight = Color(0xFFFFFFFF)
    val HeadingTextLight = Color(0xFF1A1A2E)
    val BodyTextLight = Color(0xFF4A4A6A)
}

val DarkColorScheme = darkColorScheme(
    primary = PortfolioColors.Accent,
    secondary = PortfolioColors.Accent,
    background = PortfolioColors.Background,
    surface = PortfolioColors.Surface,
    surfaceVariant = PortfolioColors.Surface,
    onPrimary = PortfolioColors.Background,
    onBackground = PortfolioColors.HeadingText,
    onSurface = PortfolioColors.HeadingText,
    onSurfaceVariant = PortfolioColors.BodyText,
    error = Color(0xFFCF6679),
)

val LightColorScheme = lightColorScheme(
    primary = PortfolioColors.Accent,
    secondary = PortfolioColors.Accent,
    background = PortfolioColors.BackgroundLight,
    surface = PortfolioColors.SurfaceLight,
    surfaceVariant = PortfolioColors.SurfaceLight,
    onPrimary = PortfolioColors.BackgroundLight,
    onBackground = PortfolioColors.HeadingTextLight,
    onSurface = PortfolioColors.HeadingTextLight,
    onSurfaceVariant = PortfolioColors.BodyTextLight,
)
