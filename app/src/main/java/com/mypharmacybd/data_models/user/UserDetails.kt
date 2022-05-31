package com.mypharmacybd.data_models.user

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.data_models.address.District
import com.mypharmacybd.data_models.address.Division
import com.mypharmacybd.data_models.address.Upazila

data class UserDetails(
    @SerializedName("id") val id:Int,
    @SerializedName("zip") val zip:String?,
    @SerializedName("address_1") val address_1:String?,
    @SerializedName("address_2") val address_2:String?,
    @SerializedName("division") var division:Division?,
    @SerializedName("district") var district:District?,
    @SerializedName("upazila") val upazila:Upazila?,
    )
