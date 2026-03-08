package com.ptut.portfolio.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

object PortfolioColors {
    val Primary = Color(0xFF6C63FF)
    val PrimaryVariant = Color(0xFF4C45CC)
    val Secondary = Color(0xFF00D4FF)
    val Background = Color(0xFF0D0D0D)
    val Surface = Color(0xFF1A1A2E)
    val SurfaceVariant = Color(0xFF16213E)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnBackground = Color(0xFFE0E0E0)
    val OnSurface = Color(0xFFE0E0E0)
    val OnSurfaceVariant = Color(0xFFB0B0C0)
    val Error = Color(0xFFCF6679)

    // Light mode
    val PrimaryLight = Color(0xFF6C63FF)
    val BackgroundLight = Color(0xFFF8F8FF)
    val SurfaceLight = Color(0xFFFFFFFF)
    val SurfaceVariantLight = Color(0xFFEEEEF8)
    val OnBackgroundLight = Color(0xFF1A1A2E)
    val OnSurfaceLight = Color(0xFF1A1A2E)
    val OnSurfaceVariantLight = Color(0xFF4A4A6A)
}

val DarkColorScheme = darkColorScheme(
    primary = PortfolioColors.Primary,
    secondary = PortfolioColors.Secondary,
    background = PortfolioColors.Background,
    surface = PortfolioColors.Surface,
    surfaceVariant = PortfolioColors.SurfaceVariant,
    onPrimary = PortfolioColors.OnPrimary,
    onBackground = PortfolioColors.OnBackground,
    onSurface = PortfolioColors.OnSurface,
    onSurfaceVariant = PortfolioColors.OnSurfaceVariant,
    error = PortfolioColors.Error,
)

val LightColorScheme = lightColorScheme(
    primary = PortfolioColors.PrimaryLight,
    secondary = PortfolioColors.Secondary,
    background = PortfolioColors.BackgroundLight,
    surface = PortfolioColors.SurfaceLight,
    surfaceVariant = PortfolioColors.SurfaceVariantLight,
    onPrimary = PortfolioColors.OnPrimary,
    onBackground = PortfolioColors.OnBackgroundLight,
    onSurface = PortfolioColors.OnSurfaceLight,
    onSurfaceVariant = PortfolioColors.OnSurfaceVariantLight,
)
