package com.mypharmacybd.ui.auth.fragments.user_login.model.models

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.User

data class LoginResponse(
    @SerializedName("access_token") var accessToken: String?,
    @SerializedName("user") var user: User?,
    @SerializedName("message") var message: String?,
)