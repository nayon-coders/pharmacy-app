package com.mypharmacybd.ui.main_activity.fragments.categories.model

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.categories.CategoriesContract
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.user.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersModel(
    val apiService: ApiServices, val dbServices: PharmacyDAO
) : OrderListContract.Model {
    override fun getOrders(onFinishedListener: OrderListContract.OnFinishedListener) {
        val headerMap = ApiConfig.headerMap

        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken

        apiService.getOrders(headerMap).enqueue(
            object : Callback<GetOrderResponse>{
                override fun onResponse(call: Call<GetOrderResponse>, response: Response<GetOrderResponse>) {
                    if(response.isSuccessful){
                        response.body()?.let { onFinishedListener.onSuccessOrders(it) }
                    } else {
                        onFinishedListener.onFailedOrders(
                            APIFailure(response.code(), response.message())
                        )
                    }
                }

                override fun onFailure(call: Call<GetOrderResponse>, t: Throwable) {
                    onFinishedListener.onFailedOrders(
                        APIFailure(-1, t.message.toString())
                    )
                }

            }
        )
    }


}

