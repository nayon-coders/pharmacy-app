package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.working_models

import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.contact.PrescriptionListContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.model.AllPrescription
import com.mypharmacybd.user.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrescriptionsModel (val apiService: ApiServices, val dbServices: PharmacyDAO
) : PrescriptionListContact.Model{
    override fun getOrders(onFinishedListener: PrescriptionListContact.OnFinishedListener) {
        val headerMap = ApiConfig.headerMap

        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.getOrdersPrescription(headerMap).enqueue(
            object : Callback<AllPrescription> {
                override fun onResponse(call: Call<AllPrescription>, response: Response<AllPrescription>) {
                    if(response.isSuccessful){
                        response.body()?.let { onFinishedListener.onSuccessOrders(it) }
                    } else {
                        onFinishedListener.onFailedOrders(
                            APIFailure(response.code(), response.message())
                        )
                    }
                }

                override fun onFailure(call: Call<AllPrescription>, t: Throwable) {
                    onFinishedListener.onFailedOrders(
                        APIFailure(-1, t.message.toString())
                    )
                }

            }
        )
    }

}