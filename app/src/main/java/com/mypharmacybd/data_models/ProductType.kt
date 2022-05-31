package com.mypharmacybd.data_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductType(
    @SerializedName("id") var id:Int,
    @SerializedName("name") var name:String
) : Parcelable {
    operator fun get(s: String) {

    }
}
