package com.teammates.mallakhambindia.data.ResponseModel

import com.google.gson.annotations.SerializedName

data class UserLoginData(

    @SerializedName("success" ) var success : Boolean? = null,
    @SerializedName("token"   ) var token   : String?  = null,
    @SerializedName("year"    ) var year    : String?  = null

)