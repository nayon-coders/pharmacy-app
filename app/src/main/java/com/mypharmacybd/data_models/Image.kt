package com.mypharmacybd.data_models

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id") var id:Int,
    @SerializedName("product_id") var product_id:String?,
    @SerializedName("file_path") var file_path:String?,
    @SerializedName("created_at") var created_at:String?,
    @SerializedName("updated_at") var updated_at:String?,
)
