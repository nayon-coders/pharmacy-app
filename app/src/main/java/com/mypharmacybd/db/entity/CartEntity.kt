package com.mypharmacybd.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mypharmacybd.data_models.Product

@Entity(tableName = "product_cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = false) var productID: Int,
    var name: String?,
    var slug: String?,
    var quantity: String?,
    var mg: String?,
    var code: String?,
    var details: String?,
    var price: String?,
    var new_price: String?,
    var stock: String?,
    var point: String?,
    var status: String?,
    var is_featured: String?,
    var productType: String?,
    var productTypeId: Int?,
    var productImage: String?
) {
    fun setProduct(product:Product):CartEntity{
        this.productID = product.id
        this.name = product.name
        this.slug = product.slug
        this.quantity = 1.toString()
        this.mg = product.mg
        this.code = product.code
        this.details = product.details
        this.price = product.price
        this.new_price = product.new_price
        this.stock = product.stock
        this.point = product.point
        this.status = product.status
        this.is_featured = product.is_featured
        this.productType = product.product_type?.name
        this.productTypeId = product.product_type?.id
        this.productImage = product.product_images?.get(0)?.file_path

        return this
    }
}