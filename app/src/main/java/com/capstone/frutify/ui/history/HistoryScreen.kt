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


@Preview
@Composable
fun HistoryScreenPreview() {

}