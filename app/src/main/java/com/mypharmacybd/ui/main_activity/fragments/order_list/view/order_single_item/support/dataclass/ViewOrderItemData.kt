package com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.dataclass

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.mypharmacybd.data_models.order.get.OrderData


data class ViewOrderItemData(
@SerializedName("data")
@Expose
var data: OrderData? = null)