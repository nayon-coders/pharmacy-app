package com.mypharmacybd.prescriptionView.detailsOfPrescription

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class DeliveryMan (

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("slug")
    @Expose
    var slug: String? = null,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("phone")
    @Expose
    var phone: String? = null,

    @SerializedName("image")
    @Expose
    var image: Any? = null,

    @SerializedName("nid")
    @Expose
    var nid: String? = null,

    @SerializedName("email_verified_at")
    @Expose
    var emailVerifiedAt: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("deleted_at")
    @Expose
    var deletedAt: Any? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null
)