package com.mypharmacybd.ui.main_activity.fragments.order_list

import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.data_models.order.get.OrderData

interface OrderListContract {

    interface View{
        fun showProgressbar()
        fun hideProgressbar()

        fun setDataToView(orders: GetOrderResponse)

        fun onCategoryClicked(orders: OrderData)
    }

    interface Model {
        fun getOrders(onFinishedListener: OnFinishedListener)
    }

    interface Presenter {
        fun setView(view:View)
        fun getOrders()
    }

    interface OnFinishedListener{
        fun onSuccessOrders(categories: GetOrderResponse)
        fun onFailedOrders(apiFailure: APIFailure)
    }
}