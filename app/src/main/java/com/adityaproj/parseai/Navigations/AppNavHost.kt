package com.adityaproj.parseai.Navigations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adityaproj.parseai.Auth.LoginScreen
import com.adityaproj.parseai.Jobconfig.Configurationn
import com.adityaproj.parseai.SplashScreen
import com.adityaproj.parseai.upload.LoaderScreenload
import com.adityaproj.parseai.upload.ResumeUploadScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.SplashScreen.route,
        modifier = modifier,
        enterTransition = {  EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = {EnterTransition.None},
        popExitTransition = { ExitTransition.None }
    ) {

        composable(AppRoute.SplashScreen.route) {
            SplashScreen(navController)
        }

        composable(AppRoute.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(AppRoute.Dashboard.route) {
            DashboardScreen(rootNavController = navController)
        }

        composable(AppRoute.UploadResume.route) {
            ResumeUploadScreen(navController)
        }

        composable(AppRoute.LoaderScreen.route) {
            LoaderScreenload(navController)
        }

        composable(AppRoute.Configur.route) {
            Configurationn(navController)
        }
    }
}

