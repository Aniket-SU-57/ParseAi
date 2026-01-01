package com.adityaproj.parseai.Navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adityaproj.parseai.Auth.LoginScreen
import com.adityaproj.parseai.SplashScreen

@Composable
fun AppNavHost(modifier: Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.SplashScreen.route
    ) {

        composable(AppRoute.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(AppRoute.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(AppRoute.Dashboard.route) {
            DashboardScreen()
        }
    }
}
