package com.mypharmacybd.ui.main_activity.fragments.categories.presenter

import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.categories.CategoriesContract
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.data_models.order.get.OrderData
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract

class OrderListPresenter(val model: OrderListContract.Model)
    : OrderListContract.Presenter, OrderListContract.OnFinishedListener {

    private lateinit var view : OrderListContract.View

    override fun setView(view: OrderListContract.View) {
        this.view = view
    }

    override fun getOrders() {
        view.showProgressbar()
        model.getOrders(this)
    }



    override fun onSuccessOrders(orders: GetOrderResponse) {
        view.setDataToView(orders)
        view.hideProgressbar()
    }


    override fun onFailedOrders(apiFailure: APIFailure) {
        view.hideProgressbar()
    }

}