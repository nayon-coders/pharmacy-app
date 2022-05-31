package com.mypharmacybd.data_models.order.get

import com.google.gson.annotations.SerializedName

data class Links(

    @SerializedName("first") var first: String? = null,
    @SerializedName("last") var last: String? = null,
    @SerializedName("prev") var prev: String? = null,
    @SerializedName("next") var next: String? = null

)