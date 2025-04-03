package com.teammates.mallakhambindia.data.ResponseModel

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsResponse(

    @SerializedName("judge" ) var judge : Judge? = Judge()

)

@Serializable
data class Judge (

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("location"     ) var location    : String? = null,
    @SerializedName("judge_no"     ) var judgeNo     : Int?    = null,
    @SerializedName("judgetype"    ) var judgetype   : String? = null,
    @SerializedName("name"         ) var name        : String? = null,
    @SerializedName("username"     ) var username    : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("mobile_no"    ) var mobileNo    : String? = null,
    @SerializedName("created_date" ) var createdDate : String? = null,
    @SerializedName("updated_date" ) var updatedDate : String? = null

)
