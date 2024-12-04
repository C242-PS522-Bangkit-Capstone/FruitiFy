package com.capstone.frutify.ui.home.scan

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.frutify.R

@Composable
fun SaveScanResult(
    scanResult: String,
    imageUri: String,
    onBackClicked: () -> Unit,
    onSaveResult: () -> Unit
){

    var fruitWeight by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                        onBackClicked()
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
                    text = "Save Result",
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
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            // Tampilkan gambar
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Image",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(10.dp))
                val imageBitmap = remember(imageUri) {
                    BitmapFactory.decodeFile(Uri.parse(imageUri).path)
                }
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = "Scanned Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Text(
                        text = "Image not available",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF666666)
                    )
                }
            }

            // Tampilkan hasil analisis
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Analysis Result",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = scanResult,
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = scanResult,
                            fontSize = 14.sp
                        )
                    },
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.Check,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp),
                            tint = Color(0xFF666666)
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF333333),
                        unfocusedBorderColor = Color(0xFF333333),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedPlaceholderColor = Color(0xFF666666),
                        unfocusedPlaceholderColor = Color(0xFF666666)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Weight",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = fruitWeight,
                    onValueChange = {
                        fruitWeight = it
                    },
                    placeholder = {
                        Text(
                            text = "input the weight",
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.weight),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF333333),
                        unfocusedBorderColor = Color(0xFF333333),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedPlaceholderColor = Color(0xFF666666),
                        unfocusedPlaceholderColor = Color(0xFF666666)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Date",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = fruitWeight,
                    onValueChange = {
                        fruitWeight = it
                    },
                    placeholder = {
                        Text(
                            text = "20 November 2024",
                            fontSize = 14.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Rounded.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp),
                            tint = Color(0xFF666666)
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF333333),
                        unfocusedBorderColor = Color(0xFF333333),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedPlaceholderColor = Color(0xFF666666),
                        unfocusedPlaceholderColor = Color(0xFF666666)
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    onSaveResult()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5BB85B),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "Save",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}


@Preview
@Composable
fun SaveScanResultPreview(){
    SaveScanResult(onBackClicked = {}, onSaveResult = {}, scanResult = "", imageUri = "")
}