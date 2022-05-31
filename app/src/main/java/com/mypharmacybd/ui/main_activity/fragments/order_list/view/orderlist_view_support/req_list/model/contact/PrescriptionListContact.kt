package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.contact

import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.data_models.order.get.OrderData
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.AllPrescription

interface PrescriptionListContact {

    interface View{
        fun showProgressbar()
        fun hideProgressbar()

        fun setDataToView(prescriptions : AllPrescription)

        fun onPrescriptionItemClicked(id : Int)
    }

    interface Model {
        fun getOrders(onFinishedListener: OnFinishedListener)
    }

    interface Presenter {
        fun setView(view:View)
        fun getOrders()
    }

    interface OnFinishedListener{
        fun onSuccessOrders(prscriptions : AllPrescription)
        fun onFailedOrders(apiFailure: APIFailure)
    }
}