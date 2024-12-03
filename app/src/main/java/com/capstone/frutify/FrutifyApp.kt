package com.capstone.frutify

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capstone.frutify.ui.auth.Auth
import com.capstone.frutify.ui.history.HistoryNavHost
import com.capstone.frutify.ui.home.HomeScreenNavHost
import com.capstone.frutify.ui.onboarding.OnboardingScreen
import com.capstone.frutify.ui.setting.SettingNavHost

@Composable
fun FruitifyApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf("home", "history", "setting")
    val startDestination = "onboarding"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            composable("onboarding") {
                OnboardingScreen(
                    onClickStarted = {
                        navController.navigate("login")
                    }
                )
            }
            composable("login") {
                Auth(
                    onLogin = {
                        navController.navigate("home")
                    }
                )
            }
            composable("home") {
                HomeScreenNavHost()
            }
            composable("history") {
                HistoryNavHost()
            }
            composable("setting") {
                SettingNavHost()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color(0xFFF5F5F5)
    ) {
        val items = listOf(
            BottomNavItem("home", "Home", Icons.Rounded.Home),
            BottomNavItem("history", "History", Icons.Rounded.History),
            BottomNavItem("setting", "Setting", Icons.Rounded.Settings)
        )

        items.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFF5F5F5),
                    selectedIconColor = Color(0xFF5BB85B),
                    unselectedIconColor = Color(0xFF333333),
                    selectedTextColor = Color(0xFF5BB85B),
                    unselectedTextColor = Color(0xFF333333)
                ),
                selected = navController.currentDestination?.route == item.route,
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
                        item.icon,
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(30.dp)
                    )
                },
                label = {
                    Text(
                        item.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)