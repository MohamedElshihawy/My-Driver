package com.example.mydriver.domain.models

data class UserRegistration(
    val userName: String,
    val userEmail: String,
    val userPhoneNumber: String,
    val userPassword: String,
    val rating: Float = 0.0F,
)
