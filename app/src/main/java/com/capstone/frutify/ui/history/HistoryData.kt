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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.frutify.model.ScanData
import com.capstone.frutify.ui.home.component.RecentDataCard
import com.capstone.frutify.viewModel.ScanViewModel

@Composable
fun HistoryData(
    onClickDetail: (ScanData) -> Unit
) {
    val scanViewModel: ScanViewModel = viewModel()
    val listData by scanViewModel.scanData.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        scanViewModel.fetchScanData()
    }

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
                    image = it.fruit_image_url,
                    title = it.fruit_name,
                    date = it.scan_date,
                    weight = it.fruit_weight.toDouble(),
                    onClickDetail = {
                        onClickDetail(it)
                    }
                )
            }
        }
    }
}