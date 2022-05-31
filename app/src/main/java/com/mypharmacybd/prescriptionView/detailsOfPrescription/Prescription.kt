package com.mypharmacybd.prescriptionView.detailsOfPrescription

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Prescription (@SerializedName("data")
                         @Expose
                         var data: DataPrescription? = null)


