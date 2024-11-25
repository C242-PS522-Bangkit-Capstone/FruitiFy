package com.capstone.frutify.ui.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.capstone.frutify.ui.auth.login.LoginActivity

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterScreen(onSignUp = { /*TODO*/ }) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RegisterActivityPreview() {
    RegisterScreen(onSignUp = { /*TODO*/ }) {

    }
}