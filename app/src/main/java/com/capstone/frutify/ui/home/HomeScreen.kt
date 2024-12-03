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
import androidx.navigation.navigation
import com.capstone.frutify.ui.SuccessScreen
import com.capstone.frutify.ui.history.DetailScreen
import com.capstone.frutify.ui.home.component.FruitData
import com.capstone.frutify.ui.home.component.HomeScreenHeader
import com.capstone.frutify.ui.home.component.QuickScanOption
import com.capstone.frutify.ui.home.component.RecentData
import com.capstone.frutify.ui.home.scan.ResultScreen
import com.capstone.frutify.ui.home.scan.SaveScanResult
import com.capstone.frutify.ui.home.scan.ScanScreen

@Composable
fun HomeScreen(
    onFruitSelected: (String) -> Unit,
    onClickDetail: (FruitData) -> Unit
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 40.dp)
            ) {
                QuickScanOption(
                    onScan = { fruitName ->
                        onFruitSelected(fruitName)
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                RecentData(
                    onClickDetail = { item ->
                        onClickDetail(item)
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreenNavHost() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "home/main") {
        navigation(startDestination = "home/main", route = "home") {
            composable("home/main") {
                HomeScreen(
                    onFruitSelected = { fruitName ->
                        navController.navigate("home/scan/$fruitName")
                    },
                    onClickDetail = { item ->
                        navController.navigate(
                            "home/detail_screen?image=${item.image}&title=${item.title}&date=${item.date}&weight=${item.weight}"
                        )
                    }
                )
            }
            composable(
                route = "home/detail_screen?image={image}&title={title}&date={date}&weight={weight}",
                arguments = listOf(
                    navArgument("image") { type = NavType.StringType },
                    navArgument("title") { type = NavType.StringType },
                    navArgument("date") { type = NavType.StringType },
                    navArgument("weight") { type = NavType.FloatType }
                )
            ) { backStackEntry ->
                val image = backStackEntry.arguments?.getString("image") ?: ""
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val date = backStackEntry.arguments?.getString("date") ?: ""
                val weight = backStackEntry.arguments?.getFloat("weight") ?: 0.0f

                DetailScreen(
                    image = image,
                    title = title,
                    date = date,
                    weight = weight.toDouble(),
                    onBackClicked = {
                        navController.popBackStack(route = "home/main", inclusive = false)
                    }
                )
            }
            composable(
                "home/scan/{fruitName}",
                arguments = listOf(
                    navArgument("fruitName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val fruitName = backStackEntry.arguments?.getString("fruitName") ?: "Unknown"
                ScanScreen(
                    fruitName = fruitName,
                    onBackClicked = {
                        navController.popBackStack(
                            route = "home_screen",
                            inclusive = false
                        )
                    },
                    onScanCompleted = { scanResult, imageUri ->
                        navController.navigate("home/result_screen")
                    }
                )
            }
            composable(route = "home/result_screen"){
                ResultScreen(
                    imageUri = "",
                    scanResult = "",
                    onBackClicked = {
                        navController.popBackStack()
                    },
                    saveResult = {
                        navController.navigate("home/save_result_screen")
                    }
                )
            }
            composable(route = "home/save_result_screen"){
                SaveScanResult(
                    onBackClicked = {
                        navController.popBackStack()
                    },
                    onSaveResult = {
                        navController.navigate("home/success_screen_scan")
                    }
                )
            }
            composable(route = "home/success_screen_scan"){
                SuccessScreen(
                    onBackClicked = {
                        navController.popBackStack(route = "home/main", inclusive = false)
                    },
                    title = "Data Successfully Saved"
                )
            }
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview() {}