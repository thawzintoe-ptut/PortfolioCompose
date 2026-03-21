package com.ptut.portfolio.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ptut.portfolio.data.model.PortfolioData
import com.ptut.portfolio.theme.PortfolioColors
import com.ptut.portfolio.ui.about.AboutScreen
import com.ptut.portfolio.ui.contact.ContactScreen
import com.ptut.portfolio.ui.experience.ExperienceScreen
import com.ptut.portfolio.ui.hero.HeroScreen
import com.ptut.portfolio.ui.projects.ProjectsScreen
import com.ptut.portfolio.ui.skills.SkillsScreen
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
    NavItem(Screen.Hero, "Home", Icons.Outlined.Home),
    NavItem(Screen.About, "About", Icons.Outlined.Person),
    NavItem(Screen.Skills, "Skills", Icons.Outlined.Layers),
    NavItem(Screen.Projects, "Projects", Icons.Outlined.Star),
    NavItem(Screen.Experience, "Work", Icons.Outlined.Work),
    NavItem(Screen.Contact, "Contact", Icons.Outlined.Email),
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
            composable<Screen.Skills> {
                SkillsScreen(
                    portfolioData = portfolioData,
                    windowWidthClass = windowWidthClass,
                )
            }
            composable<Screen.Projects> {
                ProjectsScreen(
                    portfolioData = portfolioData,
                    windowWidthClass = windowWidthClass,
                )
            }
            composable<Screen.Experience> {
                ExperienceScreen(
                    portfolioData = portfolioData,
                    windowWidthClass = windowWidthClass,
                )
            }
            composable<Screen.Contact> {
                ContactScreen(
                    portfolioData = portfolioData,
                    windowWidthClass = windowWidthClass,
                )
            }
        }
    }

    when (windowWidthClass) {
        WindowWidthClass.Compact -> {
            Box(modifier = Modifier.fillMaxSize()) {
                navHost(Modifier.fillMaxSize().padding(bottom = 64.dp))

                // Minimal bottom nav — icon only, glassmorphic style
                EditorialBottomBar(
                    navItems = navItems,
                    currentRoute = currentRoute,
                    onNavigate = { navigate(it) },
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }

        WindowWidthClass.Medium -> {
            Scaffold { innerPadding ->
                Row(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
                    NavigationRail(
                        containerColor = PortfolioColors.Background,
                    ) {
                        navItems.forEach { item ->
                            NavigationRailItem(
                                selected = currentRoute?.contains(item.screen::class.simpleName ?: "") == true,
                                onClick = { navigate(item.screen) },
                                icon = { Icon(item.icon, contentDescription = item.label, tint = PortfolioColors.BodyText) },
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
                    PermanentDrawerSheet(
                        drawerContainerColor = PortfolioColors.Background,
                    ) {
                        navItems.forEach { item ->
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text = item.label,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = PortfolioColors.BodyText,
                                        maxLines = 1,
                                    )
                                },
                                icon = { Icon(item.icon, contentDescription = item.label, tint = PortfolioColors.BodyText) },
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
private fun EditorialBottomBar(
    navItems: List<NavItem>,
    currentRoute: String?,
    onNavigate: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(PortfolioColors.Background.copy(alpha = 0.85f))
            .padding(horizontal = 48.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        navItems.forEach { item ->
            val isSelected = currentRoute?.contains(item.screen::class.simpleName ?: "") == true
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onNavigate(item.screen) },
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (isSelected) PortfolioColors.Accent else PortfolioColors.BodyText,
                    modifier = Modifier.size(20.dp),
                )
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(PortfolioColors.Accent),
                    )
                }
            }
        }
    }
}
