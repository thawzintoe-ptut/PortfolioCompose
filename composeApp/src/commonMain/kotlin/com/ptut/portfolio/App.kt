package com.ptut.portfolio

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ptut.portfolio.data.repository.PortfolioRepository
import com.ptut.portfolio.navigation.AdaptivePortfolioApp
import com.ptut.portfolio.theme.PortfolioTheme
import com.ptut.portfolio.util.windowWidthClassFor

@Composable
fun App() {
    var darkTheme by remember { mutableStateOf(true) }
    val repository = remember { PortfolioRepository() }
    val portfolioData by repository.data.collectAsState()

    LaunchedEffect(Unit) {
        repository.load()
    }

    PortfolioTheme(darkTheme = darkTheme) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val windowWidthClass = windowWidthClassFor(maxWidth)
            AdaptivePortfolioApp(
                windowWidthClass = windowWidthClass,
                portfolioData = portfolioData,
                onToggleTheme = { darkTheme = !darkTheme },
                isDark = darkTheme,
            )
        }
    }
}
