package com.ptut.portfolio.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val PortfolioTypography = Typography(
    // Hero name, section headings (72sp in Figma)
    displayLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 72.sp,
        lineHeight = 79.2.sp,
        letterSpacing = (-1.44).sp,
    ),
    // Projects/Experience headings (48sp in Figma)
    displayMedium = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 48.sp,
        lineHeight = 48.sp,
        letterSpacing = (-2.4).sp,
    ),
    // Stat values (28sp in Figma)
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 42.sp,
    ),
    // Contact heading, project titles (22sp in Figma)
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 33.sp,
    ),
    // Experience role names (18sp in Figma)
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.45).sp,
    ),
    // Hero subtitle (18sp in Figma)
    titleLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        lineHeight = 27.sp,
    ),
    // Skill category headings (15sp in Figma)
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 22.5.sp,
    ),
    // Body copy (15sp in Figma)
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 24.38.sp,
    ),
    // Achievements, contact links (14sp in Figma)
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 22.75.sp,
    ),
    // Hero CTA links (16sp in Figma)
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    // Section labels: "ABOUT", "SKILLS", etc. (11sp, uppercase, 4px tracking)
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.5.sp,
        letterSpacing = 4.sp,
    ),
    // Meta/duration text (12sp, 0.6px tracking, uppercase)
    labelMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.6.sp,
    ),
    // Monospace skill items / tags (13sp)
    labelSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 21.13.sp,
        fontFamily = FontFamily.Monospace,
    ),
)
