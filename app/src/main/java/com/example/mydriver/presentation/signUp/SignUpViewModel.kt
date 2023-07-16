package com.example.mydriver.presentation.signUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydriver.domain.useCases.UseCasesWrapper
import com.example.mydriver.util.Resource
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val dbReference: DatabaseReference,
) : ViewModel() {
    private val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()

    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    private val _email = mutableStateOf("")
    val emailState: State<String> = _email

    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> = _phoneNumber

    private val _password = mutableStateOf("")
    val passwordState: State<String> = _password

    fun onEvent(signUpEvent: SignUpEvent) {
        when (signUpEvent) {
            is SignUpEvent.EnteredEmail -> {
                _email.value = signUpEvent.email
            }

            is SignUpEvent.EnteredPassword -> {
                _password.value = signUpEvent.password
            }

            is SignUpEvent.EnteredUserName -> {
                _userName.value = signUpEvent.userName
            }

            is SignUpEvent.EnteredPhoneNumber -> {
                _phoneNumber.value = signUpEvent.phoneNumber
            }

            is SignUpEvent.SignUp -> {
                viewModelScope.launch {
                    useCasesWrapper.signUpUseCase(userRegistration = signUpEvent.userRegistration)
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _signUpState.send(SignUpState(isLoading = true))
                                }

                                is Resource.Success -> {
                                    val currentUserUID = result.data!!.user!!.uid
                                    dbReference.child(currentUserUID)
                                        .setValue(signUpEvent.userRegistration)
                                        .addOnSuccessListener {
                                            createdAccount(currentUserUID)
                                        }
                                }

                                is Resource.Error -> {
                                    _signUpState.send(SignUpState(isError = "Failed to Sign Up"))
                                }
                            }
                        }
                }
            }
        }
    }

    private fun createdAccount(userId: String) {
        viewModelScope.launch {
            _signUpState.send(
                SignUpState(isSuccess = "User ID $userId created successfully"),
            )
        }
    }
}
