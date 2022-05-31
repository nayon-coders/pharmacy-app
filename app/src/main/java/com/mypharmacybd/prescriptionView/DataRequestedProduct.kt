package com.mypharmacybd.prescriptionView
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




data class DataRequestedProduct (@SerializedName("requested_products")
                                 @Expose
                                 var requestedProducts: List<RequestedProduct>? = null)

