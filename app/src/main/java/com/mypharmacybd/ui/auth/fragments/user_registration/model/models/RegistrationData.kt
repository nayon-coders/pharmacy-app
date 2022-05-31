package com.mypharmacybd.ui.auth.fragments.user_registration.model.models

import com.google.gson.annotations.SerializedName

data class RegistrationData(
    @SerializedName("name") val name:String,
    @SerializedName("email") val email:String,
    @SerializedName("phone") val phone:String,
    @SerializedName("password") val password:String
)
