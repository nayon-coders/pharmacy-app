package com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.contact

import com.google.gson.JsonObject
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.data_models.order.get.OrderData
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.dataclass.ViewOrderItemData

interface ItemOrderContact {

    interface View{
        fun showProgressbar()
        fun hideProgressbar()

        fun setDataToView(order: ViewOrderItemData)
        fun successCancelling(orderCancel : JsonObject)
        fun failedToCancel()


    }

    interface Model {
        fun getOrderItem(onFinishedListener: OnFinishedListener , id : Int)
        fun cancelOrderItem(onFinishedListener: OnFinishedListener , id : Int)
    }

    interface Presenter {
        fun setView(view:View)
        fun getOrderFromItem(id : Int)
        fun cancelOrderItem(id: Int)
    }

    interface OnFinishedListener{
        fun onSuccessOrders(order : ViewOrderItemData)
        fun onFailedOrders(apiFailure: APIFailure)
        fun onSuccessCancelingOrders(orderCancel : JsonObject)
        fun onFailedCancelingOrders(apiFailure: APIFailure)
    }
}