package com.example.mydriver.presentation.signUp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mydriver.data.remote.models.UserRegistration
import com.example.mydriver.navigation.Screen
import com.example.mydriver.presentation.common.CustomTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = getViewModel(),
    navController: NavController,
) {
    val emailState = rememberSaveable { viewModel.emailState }
    val userNameState = rememberSaveable { viewModel.userName }
    val phoneNumberState = rememberSaveable { viewModel.phoneNumber }
    val passwordState = rememberSaveable { viewModel.passwordState }
    val signUpState = viewModel.signUpState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .matchParentSize()
                .padding(top = 100.dp),
        ) {
            Text(
                text = "Create An Account",
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
            )

            Spacer(
                modifier = Modifier.height(48.dp),
            )

            CustomTextField(
                placeHolder = "User Name",
                value = userNameState.value,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EnteredUserName(it))
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                placeHolder = "Email",
                value = emailState.value,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EnteredEmail(it))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                ),
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )
            CustomTextField(
                placeHolder = "Phone Number",
                value = phoneNumberState.value,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EnteredPhoneNumber(it))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )

            CustomTextField(
                placeHolder = "Password",
                value = passwordState.value,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.EnteredPassword(it))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                ),
                visualTransformation = PasswordVisualTransformation(),
            )

            Spacer(
                modifier = Modifier.height(36.dp),
            )

            Button(
                modifier = Modifier
                    .width(140.dp)
                    .wrapContentHeight(),
                onClick = {
                    viewModel.onEvent(
                        SignUpEvent.SignUp(
                            UserRegistration(
                                userName = userNameState.value,
                                userEmail = emailState.value,
                                userPhoneNumber = phoneNumberState.value,
                                userPassword = passwordState.value,
                            ),
                        ),
                    )
                },
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Or Already a user ? ",
                    fontSize = 16.sp,
                )
                Text(
                    text = "Sign In",
                    fontSize = 18.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.SignIn.route)
                    },
                )
            }

            LaunchedEffect(key1 = signUpState.value?.isSuccess) {
                scope.launch {
                    if (signUpState.value?.isSuccess?.isNotEmpty() == true) {
                        Toast.makeText(context, signUpState.value?.isSuccess, Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate(Screen.SignIn.route)
                    }
                }
            }
            LaunchedEffect(key1 = signUpState.value?.isError) {
                scope.launch {
                    if (signUpState.value?.isError?.isNotEmpty() == true) {
                        Toast.makeText(context, signUpState.value?.isError, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    if (signUpState.value?.isLoading == true) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            CircularProgressIndicator(
                strokeWidth = 5.dp,
                modifier = Modifier.size(80.dp),
            )
        }
    }
}
