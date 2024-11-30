package com.capstone.frutify.ui.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.frutify.ui.home.component.FruitData
import com.capstone.frutify.ui.home.component.RecentDataCard

@Composable
fun HistoryData(
    onClickDetail: (FruitData) -> Unit
) {

    val listData = listOf(
        FruitData(
            image = "https://images.unsplash.com/photo-1531326240216-7b04ad593229?q=80&w=1980&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            title = "Apple",
            weight = 200.0,
            date = "17 November 2024"
        ),
        FruitData(
            image = "https://images.unsplash.com/photo-1531326240216-7b04ad593229?q=80&w=1980&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            title = "Pisang",
            weight = 200.0,
            date = "17 November 2024"
        ),
        FruitData(
            image = "https://images.unsplash.com/photo-1531326240216-7b04ad593229?q=80&w=1980&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            title = "Semangka",
            weight = 200.0,
            date = "17 November 2024"
        ),
        FruitData(
            image = "https://images.unsplash.com/photo-1531326240216-7b04ad593229?q=80&w=1980&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            title = "Jambu",
            weight = 200.0,
            date = "17 November 2024"
        ),
        FruitData(
            image = "https://images.unsplash.com/photo-1531326240216-7b04ad593229?q=80&w=1980&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            title = "Mangga",
            weight = 200.0,
            date = "17 November 2024"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Your History",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listData) {
                RecentDataCard(
                    image = it.image,
                    title = it.title,
                    date = it.date,
                    weight = it.weight,
                    onClickDetail = {
                        onClickDetail(it)
                    }
                )
            }
        }
    }
}