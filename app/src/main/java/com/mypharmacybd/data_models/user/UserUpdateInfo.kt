package com.mypharmacybd.data_models.user

import com.google.gson.annotations.SerializedName

class UserUpdateInfo(
    @SerializedName("name") var name:String,
    @SerializedName("phone") var phone:String,
    @SerializedName("division") var division:Int,
    @SerializedName("district") var district:Int,
    @SerializedName("upazila") var upazila:Int,
    @SerializedName("address_1") var address:String,
    @SerializedName("zip") var zip:Int,
    @SerializedName("image") var image:String,
    @SerializedName("email") var email:String,
    @SerializedName("password") var password:String,
    @SerializedName("password_confirmation") var passwordConfirmation:String,
)