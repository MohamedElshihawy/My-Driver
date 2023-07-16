package com.example.mydriver.presentation.signIn

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydriver.data.remote.models.UserRegistration
import com.example.mydriver.domain.useCases.UseCasesWrapper
import com.example.mydriver.util.Resource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val useCasesWrapper: UseCasesWrapper,
    private val dbReference: DatabaseReference,
) : ViewModel() {

    private val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    private val _email = mutableStateOf("")
    val emailState: State<String> = _email
    private val _password = mutableStateOf("")
    val passwordState: State<String> = _password

    fun onEvent(signInEvent: SignInEvent) {
        when (signInEvent) {
            is SignInEvent.EnteredEmail -> {
                _email.value = signInEvent.email
            }

            is SignInEvent.EnteredPassword -> {
                _password.value = signInEvent.password
            }

            is SignInEvent.SignIn -> {
                viewModelScope.launch {
                    useCasesWrapper.signInUseCase(signInEvent.email, signInEvent.password)
                        .collect { result ->
                            when (result) {
                                is Resource.Loading -> {
                                    _signInState.send(SignInState(isLoading = true))
                                }

                                is Resource.Success -> {
                                    var validUser = false
                                    val currentUser = result.data?.user?.uid
                                    dbReference.child(currentUser!!)
                                        .addListenerForSingleValueEvent(object :
                                            ValueEventListener {
                                            override fun onDataChange(snapshot: DataSnapshot) {
                                                if (snapshot.exists()) {
                                                    val user =
                                                        snapshot.getValue(UserRegistration::class.java)
                                                    if (user != null &&
                                                        user.userEmail == signInEvent.email &&
                                                        user.userPassword == signInEvent.password
                                                    ) {
                                                        validUser = true
                                                    }
                                                }
                                            }

                                            override fun onCancelled(error: DatabaseError) {
                                            }
                                        })
                                    if (validUser) {
                                        _signInState
                                            .send(
                                                SignInState(
                                                    isSuccess = "Signed in successfully with user ID $currentUser",
                                                ),
                                            )
                                    }
                                }

                                is Resource.Error -> {
                                    _signInState.send(SignInState(isError = "Failed to Sign in"))
                                }
                            }
                        }
                }
            }
        }
    }
}
