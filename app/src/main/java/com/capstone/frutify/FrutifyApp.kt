package com.capstone.frutify

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capstone.frutify.ui.auth.Auth
import com.capstone.frutify.ui.home.HomeScreenNavHost
import com.capstone.frutify.ui.onboarding.OnboardingScreen

@Composable
fun FruitifyApp() {
    val navController = rememberNavController()

    val startDestination = "onboarding"

    NavHost(navController = navController, startDestination = startDestination) {
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
    }
}