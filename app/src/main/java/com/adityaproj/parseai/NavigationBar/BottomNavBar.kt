package com.adityaproj.parseai.NavigationBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navDeepLink
import com.adityaproj.parseai.History.HistoryScreen
import com.adityaproj.parseai.Home.Home
import com.adityaproj.parseai.Jobconfig.Configurationn
import com.adityaproj.parseai.Navigations.BottomRoute
import com.adityaproj.parseai.Settings.SettingsScreen
import com.adityaproj.parseai.upload.LoaderScreenload
import com.adityaproj.parseai.upload.ResumeUploadScreen

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
fun BottomNavBar(navController: NavHostController) {


    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.History,
        BottomNavItem.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        containerColor = Color(0xFF020617),
        tonalElevation = 0.dp
    ) {

        items.forEach { item ->

            val selected = currentRoute == item.route

            val scale by animateFloatAsState(
                targetValue = if (selected) 1.12f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "iconScale"
            )

            val tint by animateColorAsState(
                targetValue = if (selected)
                    Color(0xFF60A5FA)
                else
                    Color.White.copy(alpha = 0.6f),
                label = "iconTint"
            )

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier
                                .size(22.dp)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                },
                            tint = tint
                        )

                        AnimatedVisibility(
                            visible = selected,
                            enter = fadeIn() + slideInVertically { it / 2 },
                            exit = fadeOut()
                        ) {
                            Text(
                                text = item.label,
                                fontSize = 11.sp,
                                color = tint
                            )
                        }
                    }
                },
                alwaysShowLabel = false
            )
        }
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
            Home(navController)
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

        composable(BottomRoute.UploadResume.route) {
            ResumeUploadScreen(navController)
        }

        composable(BottomRoute.LoaderScreen.route) {
            LoaderScreenload(navController)
        }

        composable(BottomRoute.Configur.route) {
            Configurationn(navController)
        }
    }
}