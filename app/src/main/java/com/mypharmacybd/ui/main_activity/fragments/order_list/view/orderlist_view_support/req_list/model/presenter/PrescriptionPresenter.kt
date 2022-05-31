package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.presenter

import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.prescriptionView.detailsOfPrescription.Prescription
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.contact.PrescriptionListContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.AllPrescription

class PrescriptionPresenter (val model: PrescriptionListContact.Model)
    : PrescriptionListContact.Presenter, PrescriptionListContact.OnFinishedListener{
    private lateinit var view : PrescriptionListContact.View
    override fun setView(view: PrescriptionListContact.View) {
        this.view = view
    }

    override fun getOrders() {
        view.showProgressbar()
        model.getOrders(this)
    }

    override fun onSuccessOrders(prscriptions: AllPrescription) {
        view.setDataToView(prscriptions)
        view.hideProgressbar()
    }

    override fun onFailedOrders(apiFailure: APIFailure) {
        view.hideProgressbar()
    }









}