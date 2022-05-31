package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class Meta (
    @SerializedName("current_page")
    @Expose
    var currentPage: Int? = null,

    @SerializedName("from")
    @Expose
    var from: Int? = null,

    @SerializedName("last_page")
    @Expose
    var lastPage: Int? = null,

    @SerializedName("links")
    @Expose
    var links: List<Link>? = null,

    @SerializedName("path")
    @Expose
    var path: String? = null,

    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null,

    @SerializedName("to")
    @Expose
    var to: Int? = null,

    @SerializedName("total")
    @Expose
    var total: Int? = null)
