package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("first")
    @Expose
    var first: String? = null,

    @SerializedName("last")
    @Expose
    var last: String? = null,

    @SerializedName("prev")
    @Expose
    var prev: Any? = null,

    @SerializedName("next")
    @Expose
    var next: String? = null
)
