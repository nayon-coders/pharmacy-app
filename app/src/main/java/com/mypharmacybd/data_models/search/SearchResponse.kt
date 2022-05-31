package com.mypharmacybd.data_models.search

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.data_models.Product

data class SearchResponse(
    @SerializedName("data") val productList: List<Product>,
)
