package com.capstone.frutify.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.frutify.R

@Composable
fun QuickScanOption(
    onScan: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Quick Scan",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF333333)
        )

        val fruits = listOf("Apple", "Orange", "Banana", "Tomato", "Cucumber", "Strawberry", "Peach", "Pomegranate")
        val fruitImages = listOf(
            R.drawable.apple, R.drawable.orange, R.drawable.banana, R.drawable.tomato,
            R.drawable.cucumber, R.drawable.strawberry, R.drawable.peach, R.drawable.pomegranate
        )

        fruits.chunked(4).forEachIndexed { rowIndex, fruitRow ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                fruitRow.forEachIndexed { index, fruit ->
                    val imageRes = fruitImages[rowIndex * 4 + index]

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFF5F5F5))
                            .size(width = 83.dp, height = 80.dp)
                            .clickable {
                                onScan(fruit)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = fruit,
                            modifier = Modifier.size(45.dp)
                        )
                    }
                }
            }
        }
    }
}