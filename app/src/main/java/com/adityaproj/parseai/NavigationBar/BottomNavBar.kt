package com.adityaproj.parseai.NavigationBar

import android.R.attr.scaleX
import android.R.attr.scaleY
import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navDeepLink
import com.adityaproj.parseai.History.HistoryScreen
import com.adityaproj.parseai.Home.Home
import com.adityaproj.parseai.Navigations.BottomRoute
import com.adityaproj.parseai.Settings.SettingsScreen
import java.nio.file.Files.size


sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object History : BottomNavItem("history", "History", Icons.Default.CheckCircle)
    object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)
}

@Composable
fun BottomNavBar(navController: NavHostController) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.History,
        BottomNavItem.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 🌫 Glass container
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .height(80.dp)
            .background(
                color = Color.White.copy(alpha = 0.04f), // glass base
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = Color.Transparent, // IMPORTANT for glass
            tonalElevation = 0.dp
        ) {

            items.forEach { item ->

                val selected = currentRoute == item.route

                val scale by animateFloatAsState(
                    targetValue = if (selected) 1.15f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "iconScale"
                )

                val tint by animateColorAsState(
                    targetValue = if (selected)
                        Color(0xFF60A5FA) // soft blue
                    else
                        Color.White.copy(alpha = 0.65f),
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
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier
                                .size(20.dp)
                                .graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                },
                            tint = tint
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            color = tint
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    }
}

@Composable
fun BottomNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomRoute.Home.route,
        modifier = modifier
    ) {

        composable(
            route = BottomRoute.Home.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = BottomRoute.Home.deepLink
            })
        ) {
            Home()
        }

        composable(
            route = BottomRoute.History.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = BottomRoute.History.deepLink
            })
        ) {
            HistoryScreen()
        }

        composable(
            route = BottomRoute.Settings.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = BottomRoute.Settings.deepLink
            })
        ) {
            SettingsScreen()
        }
    }
}
