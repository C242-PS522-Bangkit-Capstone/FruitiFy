package com.capstone.frutify.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val gender: String
)

data class RegisterResponse(
    val message: String
)