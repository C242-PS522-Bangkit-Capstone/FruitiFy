package com.capstone.frutify.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.capstone.frutify.ui.home.component.HomeScreenHeader
import com.capstone.frutify.ui.home.component.QuickScanOption
import com.capstone.frutify.ui.home.component.RecentData
import com.capstone.frutify.ui.home.scan.ResultScreen
import com.capstone.frutify.ui.home.scan.SaveScanResult
import com.capstone.frutify.ui.home.scan.ScanScreen
import com.capstone.frutify.ui.home.scan.SuccessScreen

@Composable
fun HomeScreen(
    onFruitSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5BB85B))
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        HomeScreenHeader()
        Spacer(modifier = Modifier.height(80.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
                .padding(bottom = 80.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 40.dp)
            ) {
                QuickScanOption(
                    onScan = { fruitName -> // Terima nama buah yang dipilih
                        onFruitSelected(fruitName) // Panggil onFruitSelected dengan fruitName
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                RecentData()
            }
        }
    }
}

@Composable
fun HomeScreenNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen { fruitName ->
                navController.navigate("scan/$fruitName")
            }
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
                    navController.popBackStack()
                },
                onScanCompleted = { scanResult, imageUri ->
                    navController.navigate("result_screen")
                }
            )
        }
        composable(route = "result_screen"){
            ResultScreen(
                imageUri = "",
                scanResult = "",
                onBackClicked = {
                    navController.popBackStack()
                },
                saveResult = {
                    navController.navigate("save_result_screen")
                }
            )
        }
        composable(route = "save_result_screen"){
            SaveScanResult(
                onBackClicked = {
                    navController.popBackStack()
                },
                onSaveResult = {
                    navController.navigate("success_screen")
                }
            )
        }
        composable(route = "success_screen"){
            SuccessScreen(
                onBackClicked = {
                    navController.popBackStack(route = "home", inclusive = false)
                }
            )
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview() {}