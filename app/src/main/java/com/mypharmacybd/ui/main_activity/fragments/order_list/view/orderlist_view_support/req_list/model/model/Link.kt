package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Link (


    @SerializedName("url")
    @Expose
    var url: String? = null,

    @SerializedName("label")
    @Expose
    var label: String? = null,

    @SerializedName("active")
    @Expose
    var active: Boolean? = null,
)


