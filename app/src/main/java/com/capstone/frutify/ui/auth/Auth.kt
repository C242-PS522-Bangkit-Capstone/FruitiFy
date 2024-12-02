package com.capstone.frutify.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capstone.frutify.ui.auth.login.LoginScreen
import com.capstone.frutify.ui.auth.register.RegisterScreen

@Composable
fun Auth(
    onLogin: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLogin = {
                    onLogin()
                },
                onSignUp = {
                    navController.navigate("signup")
                }
            )
        }
        composable("signup") {
            RegisterScreen(
                onSignUp = {
                    navController.popBackStack(
                        route = "signup",
                        inclusive = false
                    )
                },
                onLogin = {
                    navController.popBackStack(
                        route = "login",
                        inclusive = false
                    )
                }
            )
        }
    }
}