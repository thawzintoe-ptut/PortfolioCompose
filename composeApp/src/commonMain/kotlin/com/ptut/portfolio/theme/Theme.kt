package com.ptut.portfolio.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun PortfolioTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val editorialColors = if (darkTheme) DarkEditorialColors else LightEditorialColors

    CompositionLocalProvider(LocalEditorialColors provides editorialColors) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = PortfolioTypography,
            content = content,
        )
    }
}
