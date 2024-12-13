package com.capstone.frutify.ui.home.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.capstone.frutify.R
import com.capstone.frutify.model.ScanData
import com.capstone.frutify.viewModel.ScanViewModel
import java.text.SimpleDateFormat


@Composable
fun RecentData(
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
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Recent Data",
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


@SuppressLint("SimpleDateFormat")
@Composable
fun RecentDataCard(
    image: String,
    title: String,
    date: String,
    weight: Double,
    onClickDetail: () -> Unit
){
    val formatedDate = SimpleDateFormat("dd MMM yyyy").format(SimpleDateFormat("yyyy-MM-dd").parse(date)!!)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                onClickDetail()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(70.dp),
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
                
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.weight),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "$weight Gr",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = Color(0xFF666666)
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(30.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = formatedDate.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }
        }
    }
}