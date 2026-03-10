package com.adityaproj.parseai.NavigationBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navDeepLink
import com.adityaproj.parseai.History.HistoryScreen
import com.adityaproj.parseai.Home.Home
import com.adityaproj.parseai.Navigations.BottomRoute
import com.adityaproj.parseai.Navigations.BottomTab
import com.adityaproj.parseai.Settings.SettingsScreen

/* -------------------- BOTTOM NAV ITEMS -------------------- */

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object History : BottomNavItem("history", "History", Icons.Default.CheckCircle)
    object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)
}

/* -------------------- BOTTOM NAV BAR -------------------- */

@Composable
fun BottomNavBar(
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color(0xFF020617)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        BottomNavItem(BottomTab.Home, currentTab, onTabSelected)
        BottomNavItem(BottomTab.History, currentTab, onTabSelected)
        BottomNavItem(BottomTab.Settings, currentTab, onTabSelected)
    }
}

@Composable
fun BottomNavItem(
    tab: BottomTab,
    currentTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit
) {

    val selected = tab == currentTab

    val icon = when (tab) {
        BottomTab.Home -> Icons.Default.Home
        BottomTab.History -> Icons.Default.CheckCircle
        BottomTab.Settings -> Icons.Default.Settings
    }

    Column(
        modifier = Modifier
            .clickable { onTabSelected(tab) }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = icon,
            contentDescription = tab.name,
            tint = if (selected) Color(0xFF60A5FA) else Color.Gray
        )

        Text(
            text = tab.name,
            fontSize = 11.sp,
            color = if (selected) Color(0xFF60A5FA) else Color.Gray
        )
    }
}
/* -------------------- BOTTOM NAV HOST -------------------- */
@Composable
fun BottomNavHost(
    navController: NavHostController,
    rootNavController: NavController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomRoute.Home.route,
        modifier = modifier
    ) {

        composable(
            route = BottomRoute.Home.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = BottomRoute.Home.deepLink }
            )
        ) {
            Home(navController = navController,
                rootNavController = rootNavController
            )

        }

        composable(
            route = BottomRoute.History.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = BottomRoute.History.deepLink }
            )
        ) {
            HistoryScreen()
        }

        composable(
            route = BottomRoute.Settings.route,
            deepLinks = listOf(
                navDeepLink { uriPattern = BottomRoute.Settings.deepLink }
            )
        ) {
            SettingsScreen(rootNavController)
        }


    }
}