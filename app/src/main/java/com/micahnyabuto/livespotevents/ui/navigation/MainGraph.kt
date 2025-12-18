package com.micahnyabuto.livespotevents.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.micahnyabuto.livespotevents.ui.screens.events.CreateEventScreen
import com.micahnyabuto.livespotevents.ui.screens.events.EventsScreen
import com.micahnyabuto.livespotevents.ui.screens.explore.ExploreScreen
import com.micahnyabuto.livespotevents.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    appNavController: NavHostController
) {
    val pagerState = rememberPagerState(initialPage = 0) { BottomNavigationItem.entries.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal),
        bottomBar = {
            Column {
                HorizontalDivider(thickness = 2.dp)
                NavigationBar(
                    tonalElevation = 0.dp,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    BottomNavigationItem.entries.forEachIndexed { index, navigationItem ->
                        val isSelected = pagerState.currentPage == index

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
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
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
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            beyondViewportPageCount = 1
        ) { page ->
            when (page) {
                0 -> EventsScreen(navController = appNavController)
                1 -> ExploreScreen()
                2 -> CreateEventScreen(navController = appNavController)
                3 -> ProfileScreen()
            }
        }
    }
}