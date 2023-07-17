package com.example.mydriver.presentation.signUp

import com.example.mydriver.domain.models.UserRegistration

sealed class SignUpEvent {

    data class SignUp(val userRegistration: UserRegistration) : SignUpEvent()

    data class EnteredUserName(val userName: String) : SignUpEvent()
    data class EnteredEmail(val email: String) : SignUpEvent()
    data class EnteredPhoneNumber(val phoneNumber: String) : SignUpEvent()
    data class EnteredPassword(val password: String) : SignUpEvent()
}
