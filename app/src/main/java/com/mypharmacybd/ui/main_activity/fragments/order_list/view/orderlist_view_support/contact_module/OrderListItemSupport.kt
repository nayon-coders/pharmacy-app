package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.contact_module

import com.google.gson.JsonObject
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.data_models.order.get.OrderData
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.prescriptionView.PrescriptionAllMedicne
import com.mypharmacybd.prescriptionView.detailsOfPrescription.Prescription
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.viewImage.PrescriptionViewResponse

interface OrderListItemSupport {

    interface View{
        fun showProgressbar()
        fun hideProgressbar()

        fun setDataListMedicineToView(allMedicine: PrescriptionAllMedicne)
        fun setDetailsOfPrescription(prescription: Prescription)
        fun orderBuyClick(product : Product)
        fun showDialogSuccessCancel(objectJson : JsonObject)
        fun showViewPrescription(objectData : PrescriptionViewResponse)
        fun failedToDisplayPrescription()
        fun failedToCancel()
    }

    interface Model {
        // get details of medicine list..
        fun getAllMedicineResponseResult(onFinishedListener: OnFinishedListener, id: Int)

        // get download..
        fun getDownloadResponse(onFinishedListener: OnFinishedListener, id:Int)

        // put cancel
        fun getCancelResponse(onFinishedListener: OnFinishedListener ,id: Int)

        //get details of prescription..
        fun getDetailsPrescriptionResult(onFinishedListener: OnFinishedListener, id: Int)
    }

    interface Presenter {
        fun setView(view:View)

        //get details of medicine..
        fun getListOfMedicine(id: Int)

        //get details of prescription..
        fun getDetails(id : Int)

        // get download
        fun downloadImagePrescription(id:Int)

        // put cancel
        fun cancelOrder(id : Int)
    }

    interface OnFinishedListener{
        fun onSuccessMedicineDataOrders(allMedicine : PrescriptionAllMedicne)
        fun onFailedMedicineDataOrders(apiFailure: APIFailure)
        fun onSuccessDetailsViewDataOrders(prescription: Prescription)
        fun onFailedDetailsViewDataOrders(apiFailure: APIFailure)
        fun onSuccessFileDownloadResponse(responseView : PrescriptionViewResponse)
        fun onFailedFileDownloadResponse(apiFailure: APIFailure)
        fun onSuccessCancelOrderResponse(response : JsonObject)
        fun onFailedCancelOrderResponse(apiFailure: APIFailure)
    }
}