package com.teammates.mallakhambindia.ui.screens.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.teammates.mallakhambindia.data.ResponseModel.UserDetailsResponse
import com.teammates.mallakhambindia.repository.MainRepository
import com.teammates.mallakhambindia.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val mainRepository: MainRepository
) : ViewModel() {

    val TAG = "HomeScreenViewModel"

    val userDetailsResponse =  sharedPreferencesHelper.getString("userDetails")

    val gson = Gson()

    val judgeData = gson.fromJson(userDetailsResponse, UserDetailsResponse::class.java)

    fun logout(){
        sharedPreferencesHelper.clear()
    }

}