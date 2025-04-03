package com.teammates.mallakhambindia.ui.screens.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.teammates.mallakhambindia.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    fun navigateAfterDelay(navController: NavController) {
        viewModelScope.launch {
            delay(3000) // 3 seconds delay
            if(sharedPreferencesHelper.getBoolean("is_logged_in")){
                navController.navigate("homescreen") {
                    popUpTo("splashScreen") { inclusive = true }
                }
            }else{
                navController.navigate("loginScreen") {
                    popUpTo("splashScreen") { inclusive = true }
                }
            }
        }
    }
}