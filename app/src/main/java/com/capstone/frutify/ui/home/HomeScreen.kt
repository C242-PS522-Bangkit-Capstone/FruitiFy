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
import com.capstone.frutify.model.ScanData
import com.capstone.frutify.ui.home.component.HomeScreenHeader
import com.capstone.frutify.ui.home.component.QuickScanOption
import com.capstone.frutify.ui.home.component.RecentData

@Composable
fun HomeScreen(
    onFruitSelected: (String) -> Unit,
    onClickDetail: (ScanData) -> Unit
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


@Preview
@Composable
fun HomeScreenPreview() {}