package com.teammates.mallakhambindia.data

import com.teammates.mallakhambindia.data.ResponseModel.LocationDataList
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getLocations(): Flow<LocationDataList>

}