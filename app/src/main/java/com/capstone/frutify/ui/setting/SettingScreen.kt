package com.capstone.frutify.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.capstone.frutify.ui.setting.component.ChangePasswordCard
import com.capstone.frutify.ui.setting.component.DeleteAccountCard
import com.capstone.frutify.ui.setting.component.LanguageCard
import com.capstone.frutify.ui.setting.component.NotificationCard
import com.capstone.frutify.ui.setting.component.PersonalInformationCard
import com.capstone.frutify.ui.setting.component.SettingScreenHeader
import com.capstone.frutify.viewModel.AuthViewModel

@Composable
fun SettingScreen(
    onClickPersonalInformation: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickLanguage: () -> Unit,
    onClickDeleteAccount: () -> Unit
) {
    val authViewModel: AuthViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        SettingScreenHeader(
            profileImage = "",
            name = authViewModel.getUserName() ?: "",
            email = authViewModel.getUserEmail() ?: ""
        )
        PersonalInformationCard(
            onClick = {
                onClickPersonalInformation()
            }
        )
        ChangePasswordCard(
            onClick = {
                onClickChangePassword()
            }
        )
        LanguageCard(
            onClick = {
                onClickLanguage()
            }
        )
        NotificationCard()
        DeleteAccountCard(
            onClick = {
                onClickDeleteAccount()
            }
        )
    }
}


@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        onClickPersonalInformation = { /*TODO*/ },
        onClickChangePassword = { /*TODO*/ },
        onClickLanguage = { /*TODO*/ },
        onClickDeleteAccount = { /*TODO*/ }
    )
}