package com.mypharmacybd.data_models.order

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.data_models.address.District
import com.mypharmacybd.data_models.address.Division
import com.mypharmacybd.data_models.address.Upazila

data class
PostOrder(
    @SerializedName("payment_type") val paymentType: String,
    @SerializedName("address_1") val address1: String,
    @SerializedName("division") val division: Int,
    @SerializedName("district") val district: Int,
    @SerializedName("upazila") val upazila: Int,
    @SerializedName("zip") val zip: Int,
    @SerializedName("shipping") val shipping: Int,
    @SerializedName("products") val productList: List<Product>
) {
    data class Product(
        @SerializedName("product_id") var Id: Int,
        @SerializedName("quantity") var quantity: String,
        @SerializedName("price") var price: Double
    )
}

