package com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.model

import com.google.gson.JsonObject
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.contact.ItemOrderContact
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.dataclass.ViewOrderItemData
import com.mypharmacybd.user.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemOrderModel (val apiService: ApiServices, val dbServices: PharmacyDAO
) : ItemOrderContact.Model{
    override fun getOrderItem(onFinishedListener: ItemOrderContact.OnFinishedListener, id: Int) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.viewOrderDetails(headerMap,id).enqueue(object : Callback<ViewOrderItemData>{
            override fun onResponse(
                call: Call<ViewOrderItemData>,
                response: Response<ViewOrderItemData>
            ) {
                if(response.isSuccessful){
                    response.body()?.let { onFinishedListener.onSuccessOrders(it) }
                }
                else{
                    onFinishedListener.onFailedOrders(
                        APIFailure(response.code(), response.message())
                    )
                }
            }

            override fun onFailure(call: Call<ViewOrderItemData>, t: Throwable) {
                onFinishedListener.onFailedOrders(
                    APIFailure(-1, t.message.toString())
                )
            }

        })
    }

    override fun cancelOrderItem(onFinishedListener: ItemOrderContact.OnFinishedListener, id: Int) {

        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.cancelSpecificOrder(headerMap,id).enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful){
                    response.body()?.let { onFinishedListener.onSuccessCancelingOrders(it) }
                }
                else{
                    onFinishedListener.onFailedCancelingOrders(
                        APIFailure(response.code(), response.message())
                    )
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                onFinishedListener.onFailedCancelingOrders(
                    APIFailure(-1, t.message.toString())
                )
            }

        })
    }

}