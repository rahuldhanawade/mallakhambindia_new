package com.teammates.mallakhambindia.data

import com.teammates.mallakhambindia.data.RequestModel.LoginRequestModel
import com.teammates.mallakhambindia.data.ResponseModel.LocationDataList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("location")
    suspend fun getLocations(): LocationDataList

    @POST("login")
    suspend fun getUserLogin(@Body loginRequest: LoginRequestModel): LocationDataList

}