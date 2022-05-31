package com.mypharmacybd.ui.main_activity.fragments.category_details.model

import com.mypharmacybd.data_models.Products
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.category_details.CategoryDetailsContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDetailsModel(
    val apiServices: ApiServices,
    val dbServices: PharmacyDAO
) : CategoryDetailsContract.Model {
    override fun triggerApi(onFinishedListener: CategoryDetailsContract.OnFinishedListener,
                            category:String) {
        apiServices.getProductByCategory(category = category).enqueue(
            object : Callback<Products>{
                override fun onResponse(call: Call<Products>, response: Response<Products>) {
                    if(response.isSuccessful){
                        response.body()?.let { onFinishedListener.onSuccessGetProduct(it) }
                    } else {
                        onFinishedListener.onFailureGetProduct(
                            APIFailure(response.code(), response.message())
                        )
                    }
                }

                override fun onFailure(call: Call<Products>, t: Throwable) {
                    onFinishedListener.onFailureGetProduct(
                        APIFailure(-1, t.message.toString())
                    )
                }

            }
        )
    }


}