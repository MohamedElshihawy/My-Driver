package com.example.mydriver.data.remote.models

data class UserRegistration(
    val userName: String,
    val userEmail: String,
    val userPhoneNumber: String,
    val userPassword: String,
    val rating: Float = 0.0F,
)
