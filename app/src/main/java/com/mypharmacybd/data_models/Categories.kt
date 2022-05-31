package com.mypharmacybd.data_models

import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("data")
    var data: List<Category> = listOf()
)