package com.mypharmacybd.data_models.order

import com.google.gson.annotations.SerializedName

data class PostProduct(
    @SerializedName("product_id") val productID:Int,
    @SerializedName("quantity") val quantity:String,
    @SerializedName("price") val price:Double,
)
