package com.teammates.mallakhambindia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teammates.mallakhambindia.ui.screens.login.LoginScreen
import com.teammates.mallakhambindia.ui.screens.splashscreen.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destinations.SplashScreen.route) {
        composable(Destinations.SplashScreen.route) { SplashScreen(navController) }
        composable(Destinations.LoginScreen.route) { LoginScreen(navController) }
    }
}
