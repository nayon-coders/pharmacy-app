package com.mypharmacybd.prescriptionView

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class PrescriptionAllMedicne (
    @SerializedName("data")
    @Expose
    var data: DataRequestedProduct? = null
)
