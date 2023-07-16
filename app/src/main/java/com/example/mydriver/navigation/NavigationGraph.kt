package com.example.mydriver.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mydriver.presentation.signIn.SignInScreen
import com.example.mydriver.presentation.signUp.SignUpScreen
import com.example.mydriver.presentation.splash_screen.SplashScreen

@Composable
fun NavigationGraphSetUp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}
