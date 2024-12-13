package com.capstone.frutify

import android.net.Uri
import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.capstone.frutify.ui.SuccessScreen
import com.capstone.frutify.ui.auth.Auth
import com.capstone.frutify.ui.history.DetailScreen
import com.capstone.frutify.ui.history.HistoryScreen
import com.capstone.frutify.ui.home.HomeScreen
import com.capstone.frutify.ui.home.scan.ResultScreen
import com.capstone.frutify.ui.home.scan.SaveScanResult
import com.capstone.frutify.ui.home.scan.ScanScreen
import com.capstone.frutify.ui.onboarding.OnboardingScreen
import com.capstone.frutify.ui.setting.ChangePasswordScreen
import com.capstone.frutify.ui.setting.DeleteAccountScreen
import com.capstone.frutify.ui.setting.LanguageScreen
import com.capstone.frutify.ui.setting.PersonalInformationScreen
import com.capstone.frutify.ui.setting.SettingScreen
import com.capstone.frutify.viewModel.AuthViewModel

@Composable
fun FruitifyApp() {
    val authViewModel: AuthViewModel = viewModel()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Log.d("Navigation", "Current route: $currentRoute")

    val showBottomBar = currentRoute in listOf("home", "history", "setting")

    val startDestination = if (authViewModel.checkLoginStatus()) {
        "home_screen"
    } else {
        "onboarding"
    }

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

            // Onboarding Screen
            composable("onboarding") {
                OnboardingScreen(
                    onClickStarted = {
                        navController.navigate("auth")
                    }
                )
            }

            // Auth Screen
            composable("auth") {
                Auth(
                    onLogin = {
                        navController.navigate("home_screen")
                    }
                )
            }

            // Home Screen
            navigation(startDestination = "home", route = "home_screen") {
                composable("home") {
                    HomeScreen(
                        onFruitSelected = { fruitName ->
                            navController.navigate("scan/$fruitName")
                        },
                        onClickDetail = { item ->
                            navController.navigate(
                                "detail_screen?image=${item.fruit_image_url}&title=${item.fruit_name}&date=${item.scan_date}&weight=${item.fruit_weight}&nutrition=${item.nutrition_info}"
                            )
                        }
                    )
                }
                composable(
                    route = "detail_screen?image={image}&title={title}&date={date}&weight={weight}&nutrition={nutrition}",
                    arguments = listOf(
                        navArgument("image") { type = NavType.StringType },
                        navArgument("title") { type = NavType.StringType },
                        navArgument("date") { type = NavType.StringType },
                        navArgument("weight") { type = NavType.FloatType },
                        navArgument("nutrition") { type = NavType.StringType }

                    )
                ) { backStackEntry ->
                    val image = backStackEntry.arguments?.getString("image") ?: ""
                    val title = backStackEntry.arguments?.getString("title") ?: ""
                    val date = backStackEntry.arguments?.getString("date") ?: ""
                    val weight = backStackEntry.arguments?.getFloat("weight") ?: 0.0f
                    val nutrition = backStackEntry.arguments?.getString("nutrition") ?: ""

                    DetailScreen(
                        image = image,
                        title = title,
                        date = date,
                        weight = weight.toDouble(),
                        nutrition = nutrition,
                        onBackClicked = {
                            navController.popBackStack(route = "home", inclusive = false)
                        }
                    )
                }
                composable(
                    "scan/{fruitName}",
                    arguments = listOf(
                        navArgument("fruitName") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val fruitName = backStackEntry.arguments?.getString("fruitName") ?: "Unknown"
                    ScanScreen(
                        fruitName = fruitName,
                        onBackClicked = {
                            navController.popBackStack(
                                route = "home",
                                inclusive = false
                            )
                        },
                        onScanCompleted = { scanResult, imageUri ->
                            navController.navigate("result_screen/$scanResult/${Uri.encode(imageUri)}")
                        }
                    )
                }
                composable(
                    "result_screen/{scanResult}/{imageUri}",
                    arguments = listOf(
                        navArgument("scanResult") { type = NavType.StringType },
                        navArgument("imageUri") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val scanResult = backStackEntry.arguments?.getString("scanResult") ?: "Unknown"
                    val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""

                    ResultScreen(
                        imageUri = imageUri,
                        scanResult = scanResult,
                        onBackClicked = {
                            navController.popBackStack()
                        },
                        saveResult = {
                            navController.navigate("save_result_screen/$scanResult/${Uri.encode(imageUri)}")
                        }
                    )
                }
                composable(
                    "save_result_screen/{scanResult}/{imageUri}",
                    arguments = listOf(
                        navArgument("scanResult") { type = NavType.StringType },
                        navArgument("imageUri") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val scanResult = backStackEntry.arguments?.getString("scanResult") ?: "Unknown"
                    val imageUri = backStackEntry.arguments?.getString("imageUri") ?: ""

                    SaveScanResult(
                        scanResult = scanResult,
                        imageUri = imageUri,
                        onBackClicked = {
                            navController.popBackStack()
                        },
                        onSaveResult = {
                            // Implementasi untuk menyimpan hasil
                            navController.navigate("success_screen_scan")
                        }
                    )
                }
                composable(route = "success_screen_scan") {
                    SuccessScreen(
                        onBackClicked = {
                            navController.popBackStack(route = "home", inclusive = false)
                        },
                        title = "Data Successfully Saved"
                    )
                }
            }

            // History Screen
            navigation(startDestination = "history", route = "history_screen"){
                composable("history") {
                    HistoryScreen(
                        onClickDetail = { item ->
                            navController.navigate(
                                "detail_screen?image=${item.fruit_image_url}&title=${item.fruit_name}&date=${item.scan_date}&weight=${item.fruit_weight}&nutrition=${item.nutrition_info}"
                            )
                        }
                    )
                }
                composable(
                    route = "detail_screen?image={image}&title={title}&date={date}&weight={weight}&nutrition={nutrition}",
                    arguments = listOf(
                        navArgument("image") { type = NavType.StringType },
                        navArgument("title") { type = NavType.StringType },
                        navArgument("date") { type = NavType.StringType },
                        navArgument("weight") { type = NavType.FloatType },
                        navArgument("nutrition") { type = NavType.StringType }

                    )
                ) { backStackEntry ->
                    val image = backStackEntry.arguments?.getString("image") ?: ""
                    val title = backStackEntry.arguments?.getString("title") ?: ""
                    val date = backStackEntry.arguments?.getString("date") ?: ""
                    val weight = backStackEntry.arguments?.getFloat("weight") ?: 0.0f
                    val nutrition = backStackEntry.arguments?.getString("nutrition") ?: ""

                    DetailScreen(
                        image = image,
                        title = title,
                        date = date,
                        weight = weight.toDouble(),
                        nutrition = nutrition,
                        onBackClicked = {
                            navController.popBackStack(route = "home", inclusive = false)
                        }
                    )
                }
            }

            // Setting Screen
            navigation(startDestination = "setting", route = "setting_screen"){
                composable("setting") {
                    SettingScreen(
                        onClickPersonalInformation = {
                            navController.navigate("personal_information_screen")
                        },
                        onClickChangePassword = {
                            navController.navigate("change_password_screen")
                        },
                        onClickLanguage = {
                            navController.navigate("language_screen")
                        },
                        onClickDeleteAccount = {
                            navController.navigate("delete_account_screen")
                        }
                    )
                }
                composable("personal_information_screen") {
                    PersonalInformationScreen {
                        navController.popBackStack(route = "setting", inclusive = false)
                    }
                }
                composable("change_password_screen") {
                    ChangePasswordScreen {
                        navController.popBackStack(route = "setting", inclusive = false)
                    }
                }
                composable("language_screen") {
                    LanguageScreen {
                        navController.popBackStack(route = "setting", inclusive = false)
                    }
                }
                composable("delete_account_screen") {
                    DeleteAccountScreen(
                        onClickBack = {
                            navController.popBackStack(route = "setting", inclusive = false)
                        },
                        onClickDelete = {
                            navController.navigate("success_screen_setting")
                        }
                    )
                }
                composable("success_screen_setting") {
                    SuccessScreen(
                        onBackClicked = {
                            navController.popBackStack(route = "onboarding", inclusive = false)
                        },
                        title = "Account Successfully Delete"
                    )
                }
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