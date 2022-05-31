package com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.presenter

import com.google.gson.JsonObject
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.contact.ItemOrderContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.dataclass.ViewOrderItemData

class ItemOrderPresenter (val model: ItemOrderContact.Model)
    : ItemOrderContact.Presenter, ItemOrderContact.OnFinishedListener{


    private lateinit var view : ItemOrderContact.View
    override fun setView(view: ItemOrderContact.View) {
        this.view = view
    }

    override fun getOrderFromItem(id: Int) {
       view.showProgressbar()
       model.getOrderItem(this,id)
    }

    override fun cancelOrderItem(id: Int) {
        view.showProgressbar()
       model.cancelOrderItem(this,id)
    }

    override fun onSuccessOrders(order: ViewOrderItemData) {
        view.setDataToView(order)
        view.hideProgressbar()
    }

    override fun onFailedOrders(apiFailure: APIFailure) {
        view.hideProgressbar()
    }

    override fun onSuccessCancelingOrders(orderCancel: JsonObject) {
        view.successCancelling(orderCancel)
        view.hideProgressbar()
    }

    override fun onFailedCancelingOrders(apiFailure: APIFailure) {
        view.failedToCancel()
        view.hideProgressbar()
    }

}