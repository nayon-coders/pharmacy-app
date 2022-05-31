package com.mypharmacybd.data_models.slider

import com.google.gson.annotations.SerializedName

data class SliderData(
    @SerializedName("data") val data: List<Slider>
)

data class Slider(
    @SerializedName("image") val image: String
)