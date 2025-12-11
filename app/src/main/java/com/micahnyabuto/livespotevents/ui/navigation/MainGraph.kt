package com.micahnyabuto.livespotevents.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import com.micahnyabuto.livespotevents.ui.screens.events.CreateEventScreen
import com.micahnyabuto.livespotevents.ui.screens.events.EventsScreen
import com.micahnyabuto.livespotevents.ui.screens.explore.ExploreScreen
import com.micahnyabuto.livespotevents.ui.screens.profile.ProfileScreen
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    // This is the App-level NavController, for navigating *out* of the main layout
    appNavController: NavHostController
) {
    // This is the NavController for the bottom bar tabs.
    val bottomBarNavController = rememberNavController()
    val navBackStackEntry by bottomBarNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomNavigation = currentRoute != Destinations.Create.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal),
        bottomBar = {
            if (showBottomNavigation) {
                Column {
                    HorizontalDivider(thickness = 2.dp)
                    NavigationBar(
                        tonalElevation = 0.dp,
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        BottomNavigationItem.entries.forEach { navigationItem ->
                            val isSelected = currentRoute == navigationItem.route
                            NavigationBarItem(
                                selected = isSelected,
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon,
                                        contentDescription = navigationItem.label
                                    )
                                },
                                label = {
                                    Text(
                                        text = navigationItem.label,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                        )
                                    )
                                },
                                onClick = {
                                    if (currentRoute != navigationItem.route) {
                                        bottomBarNavController.navigate(navigationItem.route) {
                                            // Pop up to the start destination of the graph to avoid building up a large back stack.
                                            popUpTo(bottomBarNavController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when re-selecting the same item
                                            launchSingleTop = true
                                            // Restore state when re-selecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        // This NavHost controls the content AREA of the Scaffold.
        NavHost(
            navController = bottomBarNavController, // Use the inner controller
            startDestination = Destinations.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destinations.Home.route) {
                EventsScreen(navController = appNavController)
            }
            composable(Destinations.Explore.route) {
                ExploreScreen()
            }
            composable(Destinations.Create.route) {
                CreateEventScreen(navController = bottomBarNavController)
            }
            composable(Destinations.Profile.route) {
                ProfileScreen()
            }
        }
    }
}
