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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DeleteAccountScreen(
    onClickBack: () -> Unit,
    onClickDelete: () -> Unit
) {
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
                    text = "Delete Account",
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
                text = "If you delete your account, all your data, including profile information, notes, and history, will be permanently deleted. This action cannot be undone.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF333333),
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Type REMOVE ACCOUNT below to confirm.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {

                    },
                    placeholder = {
                        Text(
                            text = "Enter text here...",
                            fontSize = 14.sp
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
                        imeAction = ImeAction.None
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

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    onClickDelete()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5252),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "Delete Account",
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
fun DeleteAccountScreenPreview() {
    DeleteAccountScreen(
        onClickBack = {},
        onClickDelete = {}
    )
}