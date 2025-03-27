package com.teammates.mallakhambindia.data

import com.teammates.mallakhambindia.data.ResponseModel.LocationDataList
import retrofit2.http.GET

interface ApiService {

    @GET("location")
    suspend fun getLocations(): LocationDataList

}