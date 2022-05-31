package com.mypharmacybd.data_models.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("email") var email: String?,
    @SerializedName("phone") var phone: String?,
    @SerializedName("slug") var slug: String?,
    @SerializedName("image") var image:String?,
    @SerializedName("details") var details: UserDetails?,



)