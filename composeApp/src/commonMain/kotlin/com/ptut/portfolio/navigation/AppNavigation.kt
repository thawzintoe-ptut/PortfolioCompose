package com.ptut.portfolio.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

// 4 bottom nav items matching Figma
private val bottomNavItems = listOf(
    NavItem(Screen.Hero, "Home", Icons.Outlined.Home),
    NavItem(Screen.Experience, "Work", Icons.Outlined.Work),
    // 3rd item is Layers — handled separately as it expands into sub-menu
    NavItem(Screen.Contact, "Contact", Icons.Outlined.Email),
)

// Sub-menu items under the Layers (3rd) tab
private val layersSubItems = listOf(
    NavItem(Screen.About, "About", Icons.Outlined.Person),
    NavItem(Screen.Skills, "Skills", Icons.Outlined.Layers),
    NavItem(Screen.Projects, "Projects", Icons.Outlined.Star),
)

// Routes that belong to the Layers group
private val layersRoutes = listOf("About", "Skills", "Projects")

// All items for rail/drawer (expanded layouts)
private val allNavItems = listOf(
    NavItem(Screen.Hero, "Home", Icons.Outlined.Home),
    NavItem(Screen.Experience, "Work", Icons.Outlined.Work),
    NavItem(Screen.About, "About", Icons.Outlined.Person),
    NavItem(Screen.Skills, "Skills", Icons.Outlined.Layers),
    NavItem(Screen.Projects, "Projects", Icons.Outlined.Star),
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

                // 4-item bottom nav with Layers sub-menu — matching Figma
                EditorialBottomBar(
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
                        allNavItems.forEach { item ->
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
                        allNavItems.forEach { item ->
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
    currentRoute: String?,
    onNavigate: (Any) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showLayersMenu by remember { mutableStateOf(false) }
    val isLayersActive = layersRoutes.any { currentRoute?.contains(it) == true }

    Box(modifier = modifier.fillMaxWidth()) {
        // Scrim to dismiss popup — drawn first (behind everything)
        if (showLayersMenu) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { showLayersMenu = false },
            )
        }

        // Main bottom bar — 4 items
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .background(PortfolioColors.Background.copy(alpha = 0.92f))
                .padding(horizontal = 48.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Item 1: Home
            BottomNavIcon(
                item = bottomNavItems[0],
                isSelected = currentRoute?.contains("Hero") == true,
                onClick = { onNavigate(bottomNavItems[0].screen) },
            )

            // Item 2: Work/Experience
            BottomNavIcon(
                item = bottomNavItems[1],
                isSelected = currentRoute?.contains("Experience") == true,
                onClick = { onNavigate(bottomNavItems[1].screen) },
            )

            // Item 3: Layers — expands sub-menu
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { showLayersMenu = !showLayersMenu }
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Layers,
                    contentDescription = "Content",
                    tint = if (isLayersActive || showLayersMenu) PortfolioColors.Accent else PortfolioColors.BodyText,
                    modifier = Modifier.size(20.dp),
                )
                if (isLayersActive) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(PortfolioColors.Accent),
                    )
                }
            }

            // Item 4: Contact
            BottomNavIcon(
                item = bottomNavItems[2],
                isSelected = currentRoute?.contains("Contact") == true,
                onClick = { onNavigate(bottomNavItems[2].screen) },
            )
        }

        // Sub-menu popup above the bottom bar — drawn last (on top of everything)
        AnimatedVisibility(
            visible = showLayersMenu,
            enter = fadeIn(tween(200)) + slideInVertically(tween(250)) { it },
            exit = fadeOut(tween(150)) + slideOutVertically(tween(200)) { it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 72.dp),
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(PortfolioColors.Surface)
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                layersSubItems.forEach { subItem ->
                    val isSubSelected = currentRoute?.contains(subItem.screen::class.simpleName ?: "") == true
                    Row(
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                onNavigate(subItem.screen)
                                showLayersMenu = false
                            }
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Icon(
                            imageVector = subItem.icon,
                            contentDescription = subItem.label,
                            tint = if (isSubSelected) PortfolioColors.Accent else PortfolioColors.HeadingText,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = subItem.label,
                            style = MaterialTheme.typography.labelMedium.copy(
                                letterSpacing = 1.sp,
                            ),
                            color = if (isSubSelected) PortfolioColors.Accent else PortfolioColors.HeadingText,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavIcon(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
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
