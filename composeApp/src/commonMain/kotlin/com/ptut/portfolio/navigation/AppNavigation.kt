package com.ptut.portfolio.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.ui.about.AboutScreen
import com.ptut.portfolio.ui.hero.HeroScreen
import com.ptut.portfolio.util.WindowWidthClass
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable data object Hero : Screen
    @Serializable data object About : Screen
    @Serializable data object Skills : Screen
    @Serializable data object Projects : Screen
    @Serializable data object Experience : Screen
    @Serializable data object Contact : Screen
}

private data class NavItem(
    val screen: Any,
    val label: String,
    val icon: ImageVector,
)

private val navItems = listOf(
    NavItem(Screen.Hero, "Home", Icons.Default.Home),
    NavItem(Screen.About, "About", Icons.Default.Info),
    NavItem(Screen.Skills, "Skills", Icons.Default.Build),
    NavItem(Screen.Projects, "Projects", Icons.Default.Star),
    NavItem(Screen.Experience, "Experience", Icons.Default.Work),
    NavItem(Screen.Contact, "Contact", Icons.Default.Email),
)

@Composable
fun AdaptivePortfolioApp(
    windowWidthClass: WindowWidthClass,
    portfolioData: PortfolioData?,
    onToggleTheme: () -> Unit,
    isDark: Boolean = true,
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    fun navigate(screen: Any) {
        navController.navigate(screen) {
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navHost: @Composable (Modifier) -> Unit = { modifier ->
        NavHost(
            navController = navController,
            startDestination = Screen.Hero,
            modifier = modifier,
        ) {
            composable<Screen.Hero> {
                HeroScreen(
                    portfolioData = portfolioData,
                    onViewProjects = { navigate(Screen.Projects) },
                    onToggleTheme = onToggleTheme,
                    isDark = isDark,
                )
            }
            composable<Screen.About> {
                AboutScreen(
                    portfolioData = portfolioData,
                    windowWidthClass = windowWidthClass,
                )
            }
            composable<Screen.Skills> { ComingSoon("Skills") }
            composable<Screen.Projects> { ComingSoon("Projects") }
            composable<Screen.Experience> { ComingSoon("Experience") }
            composable<Screen.Contact> { ComingSoon("Contact") }
        }
    }

    when (windowWidthClass) {
        WindowWidthClass.Compact -> {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        navItems.forEach { item ->
                            NavigationBarItem(
                                selected = currentRoute?.contains(item.screen::class.simpleName ?: "") == true,
                                onClick = { navigate(item.screen) },
                                icon = { Icon(item.icon, contentDescription = item.label) },
                                label = { Text(item.label) },
                            )
                        }
                    }
                },
            ) { innerPadding ->
                navHost(Modifier.padding(innerPadding))
            }
        }

        WindowWidthClass.Medium -> {
            Scaffold { innerPadding ->
                Row(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    NavigationRail {
                        navItems.forEach { item ->
                            NavigationRailItem(
                                selected = currentRoute?.contains(item.screen::class.simpleName ?: "") == true,
                                onClick = { navigate(item.screen) },
                                icon = { Icon(item.icon, contentDescription = item.label) },
                                label = { Text(item.label) },
                            )
                        }
                    }
                    navHost(Modifier.weight(1f).fillMaxHeight())
                }
            }
        }

        WindowWidthClass.Expanded -> {
            PermanentNavigationDrawer(
                drawerContent = {
                    PermanentDrawerSheet {
                        navItems.forEach { item ->
                            NavigationDrawerItem(
                                label = { Text(item.label) },
                                icon = { Icon(item.icon, contentDescription = item.label) },
                                selected = currentRoute?.contains(item.screen::class.simpleName ?: "") == true,
                                onClick = { navigate(item.screen) },
                            )
                        }
                    }
                },
            ) {
                navHost(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun ComingSoon(name: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text("$name — Coming Soon")
    }
}
