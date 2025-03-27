package com.teammates.mallakhambindia.data

import com.teammates.mallakhambindia.data.ResponseModel.LocationDataList
import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun getLocations()= flow {
        emit(apiService.getLocations())
    }
}