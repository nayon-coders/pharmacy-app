package com.mypharmacybd.data_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String?,
    @SerializedName("slug") var slug: String?,
    @SerializedName("icon") var icon: String?,
    @SerializedName("home_view") var home_view: String?,
    @SerializedName("product") var products: Products?
) : Parcelable