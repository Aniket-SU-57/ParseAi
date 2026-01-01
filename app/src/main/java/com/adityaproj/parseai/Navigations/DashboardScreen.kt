package com.adityaproj.parseai.Navigations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adityaproj.parseai.NavigationBar.BottomNavBar
import com.adityaproj.parseai.NavigationBar.BottomNavHost

@Composable
fun DashboardScreen() {

    val bottomNavController = rememberNavController()
    val backStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val bottomBarVisible = currentRoute in listOf(
        BottomRoute.Home.route,
        BottomRoute.History.route,
        BottomRoute.Settings.route
    )

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = bottomBarVisible) {
                BottomNavBar(bottomNavController)
            }
        }
    ) { padding ->

        BottomNavHost(
            navController = bottomNavController,
            modifier = Modifier.padding(padding)
        )
    }
}
