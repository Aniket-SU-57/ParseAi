package com.adityaproj.parseai.Navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.adityaproj.parseai.History.HistoryScreen
import com.adityaproj.parseai.Home.Home
import com.adityaproj.parseai.NavigationBar.BottomNavBar
import com.adityaproj.parseai.Settings.SettingsScreen

enum class BottomTab {
    Home,
    History,
    Settings
}

@Composable
fun DashboardScreen(rootNavController: NavController) {

    var currentTab by rememberSaveable { mutableStateOf(BottomTab.Home) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentTab = currentTab,
                onTabSelected = { currentTab = it }
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // Home stays alive
            if (currentTab == BottomTab.Home) {
                Home(
                    navController = rootNavController,
                    rootNavController = rootNavController
                )
            }

            // History stays alive
            if (currentTab == BottomTab.History) {
                HistoryScreen()
            }

            // Settings stays alive
            if (currentTab == BottomTab.Settings) {
                SettingsScreen(rootNavController)
            }
        }
    }
}