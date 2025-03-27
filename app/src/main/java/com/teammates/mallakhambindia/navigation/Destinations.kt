package com.teammates.mallakhambindia.navigation

sealed class Destinations(val route: String) {
    object SplashScreen : Destinations("splashscreen")
    object LoginScreen : Destinations("loginscreen")
}