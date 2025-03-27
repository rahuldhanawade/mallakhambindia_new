package com.teammates.mallakhambindia.data.ResponseModel

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDataList (

    @SerializedName("success" ) var success : Boolean?          = null,
    @SerializedName("message" ) var message : String?           = null,
    @SerializedName("data"    ) var data    : ArrayList<String> = arrayListOf()

)