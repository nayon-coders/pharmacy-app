package com.mypharmacybd.data_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Products(
    @SerializedName("data") val data:List<Product> = listOf()
) : Parcelable
