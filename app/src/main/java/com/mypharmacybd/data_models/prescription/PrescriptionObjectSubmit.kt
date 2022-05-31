package com.mypharmacybd.data_models.prescription

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class PrescriptionObjectSubmit (
    @SerializedName("file")
    var file: PrescriptionPCSubmit,

    @SerializedName("division")
    var division: String ,

    @SerializedName("district")
    var district: String,

    @SerializedName("upazila")
    var upazila: String,

    @SerializedName("address")
    var address: String

)