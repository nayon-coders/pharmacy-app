package com.mypharmacybd.data_models.order.get

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Division(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

): Parcelable