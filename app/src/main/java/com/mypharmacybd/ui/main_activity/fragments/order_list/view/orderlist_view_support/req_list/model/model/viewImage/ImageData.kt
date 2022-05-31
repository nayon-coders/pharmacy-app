package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.viewImage

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




data class ImageData (  @SerializedName("id")
                        @Expose
                        var id: Int? = null,

                        @SerializedName("file")
                        @Expose
                        var file: String? = null)


