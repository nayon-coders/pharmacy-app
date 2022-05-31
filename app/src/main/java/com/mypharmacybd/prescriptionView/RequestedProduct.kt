package com.mypharmacybd.prescriptionView

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.data_models.Product


data class RequestedProduct ( @SerializedName("id")
                              @Expose
                              var id: Int? = null,

                              @SerializedName("quantity")
                              @Expose
                              var quantity: String? = null,

                              @SerializedName("product")
                              @Expose
                              var product: Product? = null)


