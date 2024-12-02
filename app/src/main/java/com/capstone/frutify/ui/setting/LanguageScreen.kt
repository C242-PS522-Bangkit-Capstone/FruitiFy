package com.capstone.frutify.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LanguageScreen(
    onClickBack: () -> Unit,
) {

    var isEnglishSelected by remember { mutableStateOf(true) }
    var isIndonesianSelected by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        onClickBack()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFF333333),
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                Text(
                    text = "Language",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
                Icon(
                    Icons.Rounded.MoreVert,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(
                text = "Select the language you want to use in the app. The changes will be applied immediately.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF333333),
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Indonesia",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    lineHeight = 30.sp
                )

                Switch(
                    checked = isIndonesianSelected,
                    onCheckedChange = {
                        isIndonesianSelected = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(0xFF5BB85B),
                        checkedBorderColor = Color(0xFF5BB85B),
                        checkedThumbColor = Color.White,
                        uncheckedTrackColor = Color.White,
                        uncheckedThumbColor = Color(0xFF666666),
                        uncheckedBorderColor = Color(0xFF666666),
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "English",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    lineHeight = 30.sp
                )

                Switch(
                    checked = isEnglishSelected,
                    onCheckedChange = {
                        isEnglishSelected = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color(0xFF5BB85B),
                        checkedBorderColor = Color(0xFF5BB85B),
                        checkedThumbColor = Color.White,
                        uncheckedTrackColor = Color.White,
                        uncheckedThumbColor = Color(0xFF666666),
                        uncheckedBorderColor = Color(0xFF666666),
                    )
                )
            }
        }
    }
}


@Preview
@Composable
fun LanguageScreenPreview() {
    LanguageScreen(
        onClickBack = {}
    )

}