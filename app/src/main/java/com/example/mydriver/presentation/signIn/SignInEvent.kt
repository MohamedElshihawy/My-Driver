package com.example.mydriver.presentation.signIn

sealed class SignInEvent {

    data class EnteredEmail(val email: String) : SignInEvent()
    data class EnteredPassword(val password: String) : SignInEvent()
    data class SignIn(val email: String, val password: String) : SignInEvent()
}
