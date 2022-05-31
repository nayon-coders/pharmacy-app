package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.prescriptionView.detailsOfPrescription.DataPrescription
import com.mypharmacybd.prescriptionView.detailsOfPrescription.Prescription


data class AllPrescription (@SerializedName("data")
                            @Expose
                            var data: List<DataPrescription>? = null,

                            @SerializedName("links")
                            @Expose
                            var links: Links? = null,

                            @SerializedName("meta")
                            @Expose
                            var meta: Meta? = null)

