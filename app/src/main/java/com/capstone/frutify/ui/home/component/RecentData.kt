package com.capstone.frutify.ui.home.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.capstone.frutify.R

data class FruitData(
    val image: String,
    val title: String,
    val date: String,
    val weight: Double
)

@Composable
fun RecentData(
    onClickDetail: (FruitData) -> Unit
) {

    val listData = listOf(
        FruitData(
            image = "https://images.unsplash.com/photo-1531326240216-7b04ad593229?q=80&w=1980&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            title = "Pisang",
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
            title = "Pisang",
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
            title = "Pisang",
            weight = 200.0,
            date = "17 November 2024"
        )
    )

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


@Composable
fun RecentDataCard(
    image: String,
    title: String,
    date: String,
    weight: Double,
    onClickDetail: () -> Unit
){
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
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = title,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(70.dp)
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
                            text = date,
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