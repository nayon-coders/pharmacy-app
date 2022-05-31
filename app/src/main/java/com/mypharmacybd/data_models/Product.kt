package com.mypharmacybd.data_models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String?,
    @SerializedName("generic_name") var generic_name: String?,
    @SerializedName("slug") var slug: String?,
    @SerializedName("mg") var mg: String?,
    @SerializedName("code") var code: String?,
    @SerializedName("details") var details: String?,
    @SerializedName("price") var price: String?,
    @SerializedName("new_price") var new_price: String?,
    @SerializedName("stock") var stock: String?,
    @SerializedName("point") var point: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("is_featured") var is_featured: String?,
    @SerializedName("product_type[1]") var product_type:ProductType?,
    @SerializedName("category") var category: Category?,
    @SerializedName("brand") var brand: Brand?,
    @SerializedName("sub_category") var sub_category: SubCategory?,
    @SerializedName("product_images") var product_images: List<ProductImages>?,
) : Parcelable
