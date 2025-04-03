package com.teammates.mallakhambindia.data

import com.teammates.mallakhambindia.data.RequestModel.LoginRequestModel
import com.teammates.mallakhambindia.data.ResponseModel.LocationDataList
import com.teammates.mallakhambindia.data.ResponseModel.UserDetailsResponse
import com.teammates.mallakhambindia.data.ResponseModel.UserLoginData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("location")
    suspend fun getLocations(): LocationDataList

    @POST("login")
    suspend fun getUserLogin(@Body loginRequest: LoginRequestModel): UserLoginData

    @GET("get_user")
    suspend fun getUserDetails(@Query("token") token: String): UserDetailsResponse

}