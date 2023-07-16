package com.example.mydriver.presentation.signIn

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mydriver.navigation.Screen
import com.example.mydriver.presentation.common.CustomTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = getViewModel<SignInViewModel>(),
    navController: NavController,
) {
    val emailState = rememberSaveable { viewModel.emailState }
    val passwordState = rememberSaveable { viewModel.passwordState }
    val signInState = viewModel.signInState.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(
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
                text = "Sign In",
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
            )

            Spacer(
                modifier = Modifier.height(48.dp),
            )

            CustomTextField(
                placeHolder = "Email",
                value = emailState.value,
                onValueChange = {
                    viewModel.onEvent(SignInEvent.EnteredEmail(it))
                },
            )

            Spacer(
                modifier = Modifier.height(16.dp),
            )

            CustomTextField(
                placeHolder = "Password",
                value = passwordState.value,
                onValueChange = {
                    viewModel.onEvent(SignInEvent.EnteredPassword(it))
                },
            )

            Spacer(
                modifier = Modifier.height(36.dp),
            )

            Button(
                modifier = Modifier
                    .width(140.dp)
                    .wrapContentHeight(),
                onClick = {
                    viewModel.onEvent(SignInEvent.SignIn(emailState.value, passwordState.value))
                },
            ) {
                Text(
                    text = "Sign In",
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
                    text = "A new User ? ",
                    fontSize = 16.sp,
                )
                Text(
                    text = "Sign Up",
                    fontSize = 18.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.SignUp.route)
                    },
                )
            }

            LaunchedEffect(key1 = signInState.value?.isSuccess) {
                scope.launch {
                    if (signInState.value?.isSuccess?.isNotEmpty() == true) {
                        Toast.makeText(context, signInState.value?.isSuccess, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            LaunchedEffect(key1 = signInState.value?.isError) {
                scope.launch {
                    if (signInState.value?.isError?.isNotEmpty() == true) {
                        Toast.makeText(context, signInState.value?.isError, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            LaunchedEffect(key1 = signInState.value?.isLoading) {
                scope.launch {
                    if (signInState.value?.isLoading == true) {
                        Toast.makeText(context, signInState.value?.isError, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}
