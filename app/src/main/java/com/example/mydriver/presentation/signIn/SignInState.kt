package com.example.mydriver.presentation.signIn

data class SignInState(
    var isLoading: Boolean = false,
    var isSuccess: String = "",
    var isError: String = "",
)
