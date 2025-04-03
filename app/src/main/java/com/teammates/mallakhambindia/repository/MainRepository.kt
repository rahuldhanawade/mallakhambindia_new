package com.teammates.mallakhambindia.repository

import com.teammates.mallakhambindia.data.ApiService
import com.teammates.mallakhambindia.data.RequestModel.LoginRequestModel
import com.teammates.mallakhambindia.data.ResponseModel.LocationDataList
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {

    fun getLocations()= flow {
        emit(apiService.getLocations())
    }

    fun getUserLogin(loginRequest: LoginRequestModel) = flow {
        emit(apiService.getUserLogin(loginRequest))
    }

}