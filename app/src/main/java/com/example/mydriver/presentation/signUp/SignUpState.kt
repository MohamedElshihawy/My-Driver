package com.example.mydriver.presentation.signUp

data class SignUpState(
    var isLoading: Boolean = false,
    var isSuccess: String = "",
    var isError: String = "",
)
