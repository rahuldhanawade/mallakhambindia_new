package com.teammates.mallakhambindia.ui.screens.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel () : ViewModel() {

    fun navigateAfterDelay(navController: NavController) {
        viewModelScope.launch {
            delay(3000) // 3 seconds delay
            navController.navigate("loginScreen") {
                popUpTo("splashScreen") { inclusive = true }
            }
        }
    }
}