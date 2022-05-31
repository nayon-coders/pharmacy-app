package com.mypharmacybd.data_models.user

import com.google.gson.annotations.SerializedName

data class UserUpdateInfoResponse (
    @SerializedName("message") val message:String
)