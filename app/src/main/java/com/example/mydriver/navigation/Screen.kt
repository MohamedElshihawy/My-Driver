package com.example.mydriver.navigation

sealed class Screen(val route: String) {

    object Splash : Screen(route = "splash_screen")
    object SignIn : Screen(route = "sign_in_screen")
    object SignUp : Screen(route = "sign_up_screen")
}
