package com.adityaproj.parseai.Navigations

sealed class AppRoute(val route: String) {
    object SplashScreen: AppRoute("splash")
    object LoginScreen: AppRoute("login")
    object Dashboard : AppRoute("dashboard")

    object UploadResume : AppRoute("upload")

    object LoaderScreen: AppRoute("loader")

    object Configur: AppRoute("config")

}

sealed class BottomRoute(
    val route: String,
    val deepLink: String? = null
) {
    object Home : BottomRoute("home", "parseai://home")
    object History : BottomRoute("history", "parseai://history")
    object Settings : BottomRoute("settings", "parseai://settings")


}