package com.mypharmacybd.data_models.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data") var data:User?,
    @SerializedName("message") var message:String?
)