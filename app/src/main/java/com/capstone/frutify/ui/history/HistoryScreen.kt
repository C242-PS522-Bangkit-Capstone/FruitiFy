package com.capstone.frutify.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.capstone.frutify.ui.history.component.CalendarView
import com.capstone.frutify.ui.home.component.FruitData

@Composable
fun HistoryScreen(
    onClickDetail: (FruitData) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
            .background(Color.White)
    ) {
        CalendarView()
        HistoryData(
            onClickDetail = { item ->
                onClickDetail(item)
            }
        )
    }
}

@Composable
fun HistoryNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "history_screen") {
        composable("history_screen") {
            HistoryScreen(
                onClickDetail = { item ->
                    navController.navigate(
                        "detail_screen?image=${item.image}&title=${item.title}&date=${item.date}&weight=${item.weight}"
                    )
                }
            )
        }
        composable(
            route = "detail_screen?image={image}&title={title}&date={date}&weight={weight}",
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
                    navController.popBackStack(route = "history_screen", inclusive = false)
                }
            )
        }
    }
}

@Preview
@Composable
fun HistoryScreenPreview() {
    HistoryNavHost()
}