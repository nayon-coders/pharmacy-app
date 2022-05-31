package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.presenter

import com.google.gson.JsonObject
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.prescriptionView.PrescriptionAllMedicne
import com.mypharmacybd.prescriptionView.detailsOfPrescription.Prescription
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.contact_module.OrderListItemSupport
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.viewImage.PrescriptionViewResponse


class OrderListItemSupportPresenter (val model : OrderListItemSupport.Model):OrderListItemSupport.Presenter ,OrderListItemSupport.OnFinishedListener {
    private lateinit var view : OrderListItemSupport.View

    override fun setView(view: OrderListItemSupport.View) {
        this.view = view
    }

    //get the list of medicine..
    override fun getListOfMedicine(id: Int) {
        view.showProgressbar()
        model.getAllMedicineResponseResult(this,id)
    }

    // get details of prescription..
    override fun getDetails(id: Int) {
        view.showProgressbar()
        model.getDetailsPrescriptionResult(this,id)
    }

    override fun downloadImagePrescription(id:Int) {
        view.showProgressbar()
       model.getDownloadResponse(this,id)
    }

    override fun cancelOrder(id: Int) {
        view.showProgressbar()
        model.getCancelResponse(this,id)
    }

    override fun onSuccessMedicineDataOrders(allMedicine: PrescriptionAllMedicne) {
        view.hideProgressbar()
        view.setDataListMedicineToView(allMedicine)
    }


    override fun onFailedMedicineDataOrders(apiFailure: APIFailure) {
           view.hideProgressbar()
    }

    override fun onSuccessDetailsViewDataOrders(prescription: Prescription) {
        view.hideProgressbar()
        view.setDetailsOfPrescription(prescription)
    }


    override fun onFailedDetailsViewDataOrders(apiFailure: APIFailure) {
          view.hideProgressbar()
    }

    override fun onSuccessFileDownloadResponse(objectData : PrescriptionViewResponse) {
         view.hideProgressbar()
         view.showViewPrescription(objectData)
    }

    override fun onFailedFileDownloadResponse(apiFailure: APIFailure) {
        view.hideProgressbar()
        view.failedToDisplayPrescription()
    }

    override fun onSuccessCancelOrderResponse(response : JsonObject) {
              view.hideProgressbar()
              view.showDialogSuccessCancel(response)
    }

    override fun onFailedCancelOrderResponse(apiFailure: APIFailure) {
              view.hideProgressbar()
              view.failedToCancel()
    }
}