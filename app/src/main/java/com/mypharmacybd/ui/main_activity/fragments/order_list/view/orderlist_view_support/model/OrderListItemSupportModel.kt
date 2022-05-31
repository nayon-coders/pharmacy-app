package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.model

import com.google.gson.JsonObject
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.prescriptionView.PrescriptionAllMedicne
import com.mypharmacybd.prescriptionView.detailsOfPrescription.Prescription
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.contact_module.OrderListItemSupport
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.viewImage.PrescriptionViewResponse
import com.mypharmacybd.user.Session
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class OrderListItemSupportModel(val apiService: ApiServices, val dbServices: PharmacyDAO) : OrderListItemSupport.Model {
    override fun getAllMedicineResponseResult(onFinishedListener: OrderListItemSupport.OnFinishedListener,id: Int) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.getAllMedicineList(headerMap,id).enqueue(object : Callback<PrescriptionAllMedicne>{
            override fun onResponse(
                call: Call<PrescriptionAllMedicne>,
                response: Response<PrescriptionAllMedicne>
            ) {
                if(response.isSuccessful){
                    response.body()?.let { onFinishedListener.onSuccessMedicineDataOrders(it) }
                }
                else{
                    onFinishedListener.onFailedMedicineDataOrders(
                        APIFailure(response.code(), response.message())
                    )
                }

            }

            override fun onFailure(call: Call<PrescriptionAllMedicne>, t: Throwable) {
                onFinishedListener.onFailedMedicineDataOrders(
                    APIFailure(-1, t.message.toString())
                )
            }

        })
    }

    override fun getDownloadResponse(onFinishedListener: OrderListItemSupport.OnFinishedListener , id:Int) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.viewPrescription(headerMap,id).enqueue(object : Callback<PrescriptionViewResponse>{
            override fun onResponse(
                call: Call<PrescriptionViewResponse>,
                response: Response<PrescriptionViewResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.let { onFinishedListener.onSuccessFileDownloadResponse(it) }
                }
                else{
                    onFinishedListener.onFailedFileDownloadResponse(
                        APIFailure(response.code(), response.message())
                    )
                }

            }

            override fun onFailure(call: Call<PrescriptionViewResponse>, t: Throwable) {
                onFinishedListener.onFailedFileDownloadResponse(
                    APIFailure(-1, t.message.toString())
                )
            }

        })
    }

    override fun getCancelResponse(onFinishedListener: OrderListItemSupport.OnFinishedListener ,id: Int) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.cancelReq(headerMap,id).enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful){
                    response.body()?.let { onFinishedListener.onSuccessCancelOrderResponse(it) }
                }
                else{
                    onFinishedListener.onFailedCancelOrderResponse(
                        APIFailure(response.code(), response.message())
                    )
                }

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                onFinishedListener.onFailedCancelOrderResponse(
                    APIFailure(-1, t.message.toString())
                )
            }

        })
    }

    override fun getDetailsPrescriptionResult(onFinishedListener: OrderListItemSupport.OnFinishedListener, id : Int) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.getPrescriptionDetails(headerMap,id).enqueue(object : Callback<Prescription>{
            override fun onResponse(call: Call<Prescription>, response: Response<Prescription>) {
                if(response.isSuccessful){
                    response.body()?.let { onFinishedListener.onSuccessDetailsViewDataOrders(it) }
                }
                else{
                    onFinishedListener.onFailedDetailsViewDataOrders(
                        APIFailure(response.code(), response.message())
                    )
                }
            }

            override fun onFailure(call: Call<Prescription>, t: Throwable) {
                onFinishedListener.onFailedDetailsViewDataOrders(
                    APIFailure(-1, t.message.toString())
                )
            }

        })
    }
}