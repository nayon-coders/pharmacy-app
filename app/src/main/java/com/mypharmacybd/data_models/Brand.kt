package com.mypharmacybd.data_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Brand(
    @SerializedName("id") var id:Int,
    @SerializedName("name") var name:String?,
    @SerializedName("slug") var slug:String?
) : Parcelable {

}
