package com.mypharmacybd.ui.auth.fragments.user_registration.model.models

import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("message") var message: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("errors") var errors: Errors?,
    @SerializedName("user") var user: User?
)

data class User(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("email") var email: String?,
    @SerializedName("phone") var phone: String?,
    @SerializedName("slug") var slug: String?
)

data class Errors(
    @SerializedName("name") var name: String?,
    @SerializedName("email") var error: List<String>?,
    @SerializedName("phone") var phone: List<String>?,
    @SerializedName("password") var password: List<String>?,
)

