package com.mypharmacybd.prescriptionView.detailsOfPrescription

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.data_models.address.District
import com.mypharmacybd.data_models.address.Division
import com.mypharmacybd.data_models.address.Upazila


data class DataPrescription (
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("rq_code ")
    @Expose
    var rqCode: String? = null,

    @SerializedName("division")
    @Expose
    var division: Division? = null,

    @SerializedName("district")
    @Expose
    var district: District? = null,

    @SerializedName("upazila")
    @Expose
    var upazila: Upazila? = null,

    @SerializedName("address")
    @Expose
    var address: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("note")
    @Expose
    var note: Any? = null,

    @SerializedName("delivery_man")
    @Expose
    var deliveryMan: DeliveryMan? = null
    )