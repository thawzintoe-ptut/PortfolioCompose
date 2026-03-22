package com.ptut.portfolio.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class EditorialColors(
    val accent: Color,
    val background: Color,
    val backgroundAlt: Color,
    val surface: Color,
    val headingText: Color,
    val bodyText: Color,
    val mutedText: Color,
    val metaText: Color,
    val achievementText: Color,
    val divider: Color,
)

val DarkEditorialColors = EditorialColors(
    accent = Color(0xFF6EE7B7),
    background = Color(0xFF0A0A0A),
    backgroundAlt = Color(0xFF131313),
    surface = Color(0xFF1C1B1B),
    headingText = Color(0xFFEDEDED),
    bodyText = Color(0xFF737373),
    mutedText = Color(0xFF404040),
    metaText = Color(0xFF919191),
    achievementText = Color(0xFFC6C6C6),
    divider = Color(0xFF474747),
)

val LightEditorialColors = EditorialColors(
    accent = Color(0xFF059669),
    background = Color(0xFFF8F8FF),
    backgroundAlt = Color(0xFFEFEFF5),
    surface = Color(0xFFFFFFFF),
    headingText = Color(0xFF1A1A2E),
    bodyText = Color(0xFF4A4A6A),
    mutedText = Color(0xFFB0B0C0),
    metaText = Color(0xFF6B6B85),
    achievementText = Color(0xFF3A3A55),
    divider = Color(0xFFD0D0DD),
)

val LocalEditorialColors = staticCompositionLocalOf { DarkEditorialColors }

// Backward-compatible accessor — reads from CompositionLocal
object PortfolioColors {
    val Accent: Color @Composable get() = LocalEditorialColors.current.accent
    val Background: Color @Composable get() = LocalEditorialColors.current.background
    val BackgroundAlt: Color @Composable get() = LocalEditorialColors.current.backgroundAlt
    val Surface: Color @Composable get() = LocalEditorialColors.current.surface
    val HeadingText: Color @Composable get() = LocalEditorialColors.current.headingText
    val BodyText: Color @Composable get() = LocalEditorialColors.current.bodyText
    val MutedText: Color @Composable get() = LocalEditorialColors.current.mutedText
    val MetaText: Color @Composable get() = LocalEditorialColors.current.metaText
    val AchievementText: Color @Composable get() = LocalEditorialColors.current.achievementText
    val Divider: Color @Composable get() = LocalEditorialColors.current.divider
}

val DarkColorScheme = darkColorScheme(
    primary = DarkEditorialColors.accent,
    secondary = DarkEditorialColors.accent,
    background = DarkEditorialColors.background,
    surface = DarkEditorialColors.surface,
    surfaceVariant = DarkEditorialColors.surface,
    onPrimary = DarkEditorialColors.background,
    onBackground = DarkEditorialColors.headingText,
    onSurface = DarkEditorialColors.headingText,
    onSurfaceVariant = DarkEditorialColors.bodyText,
    error = Color(0xFFCF6679),
)

val LightColorScheme = lightColorScheme(
    primary = LightEditorialColors.accent,
    secondary = LightEditorialColors.accent,
    background = LightEditorialColors.background,
    surface = LightEditorialColors.surface,
    surfaceVariant = LightEditorialColors.surface,
    onPrimary = LightEditorialColors.background,
    onBackground = LightEditorialColors.headingText,
    onSurface = LightEditorialColors.headingText,
    onSurfaceVariant = LightEditorialColors.bodyText,
)
