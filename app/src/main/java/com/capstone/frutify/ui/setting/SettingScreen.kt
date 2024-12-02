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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.capstone.frutify.ui.SuccessScreen
import com.capstone.frutify.ui.setting.component.ChangePasswordCard
import com.capstone.frutify.ui.setting.component.DeleteAccountCard
import com.capstone.frutify.ui.setting.component.LanguageCard
import com.capstone.frutify.ui.setting.component.NotificationCard
import com.capstone.frutify.ui.setting.component.PersonalInformationCard
import com.capstone.frutify.ui.setting.component.SettingScreenHeader

@Composable
fun SettingScreen(
    onClickPersonalInformation: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickLanguage: () -> Unit,
    onClickDeleteAccount: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        SettingScreenHeader(profileImage = "", name = "Raihan Maulana", email = "Raihan@gmail.com")
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

@Composable
fun SettingNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "setting_screen") {
        composable("setting_screen") {
            SettingScreen(
                onClickPersonalInformation = {
                    navController.navigate("personal_information_screen")
                },
                onClickChangePassword = {
                    navController.navigate("change_password_screen")
                },
                onClickLanguage = {
                    navController.navigate("language_screen")
                },
                onClickDeleteAccount = {
                    navController.navigate("delete_account_screen")
                }
            )
        }
        composable("personal_information_screen") {
            PersonalInformationScreen {
                navController.popBackStack(route = "setting_screen", inclusive = false)
            }
        }
        composable("change_password_screen") {
            ChangePasswordScreen {
                navController.popBackStack(route = "setting_screen", inclusive = false)
            }
        }
        composable("language_screen") {
            LanguageScreen {
                navController.popBackStack(route = "setting_screen", inclusive = false)
            }
        }
        composable("delete_account_screen") {
            DeleteAccountScreen(
                onClickBack = {
                    navController.popBackStack(route = "setting_screen", inclusive = false)
                },
                onClickDelete = {
                    navController.navigate("success_screen_setting")
                }
            )
        }
        composable("success_screen_setting") {
            SuccessScreen(
                onBackClicked = {
                    navController.popBackStack(route = "onboarding", inclusive = false)
                },
                title = "Account Successfully Delete"
            )
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingNavHost()
}