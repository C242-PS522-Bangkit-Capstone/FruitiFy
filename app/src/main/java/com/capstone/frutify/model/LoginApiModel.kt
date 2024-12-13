package com.capstone.frutify.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val token: String,
    val name: String,
    val email: String,
    val gender: String
)