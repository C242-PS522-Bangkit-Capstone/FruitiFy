package com.capstone.frutify.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.frutify.ui.auth.register.RegisterActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreen(
                onLogin = {},
                onSignUp = {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginActivityPreview() {
    LoginScreen(
        onLogin = {},
        onSignUp = {}
    )
}