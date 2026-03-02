package com.adityaproj.parseai.Navigations

sealed class AppRoute(val route: String) {
    object SplashScreen: AppRoute("splash")
    object LoginScreen: AppRoute("auth")
    object Dashboard : AppRoute("dashboard")

}

sealed class BottomRoute(
    val route: String,
    val deepLink: String? = null
) {
    object Home : BottomRoute("home", "parseai://home")
    object History : BottomRoute("history", "parseai://history")
    object Settings : BottomRoute("settings", "parseai://settings")

    // 👇 hidden screen (no bottom icon, no deep link)
    object UploadResume : BottomRoute("upload")
    object LoaderScreen: BottomRoute("loader")
}