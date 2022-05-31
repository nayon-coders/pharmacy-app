package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.viewImage

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class PrescriptionViewResponse (@SerializedName("data")
                                     @Expose
                                     var data: List<ImageData>? = null)


