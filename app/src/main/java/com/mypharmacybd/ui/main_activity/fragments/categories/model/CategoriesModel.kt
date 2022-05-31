package com.mypharmacybd.ui.main_activity.fragments.categories.model

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.categories.CategoriesContract
import com.mypharmacybd.data_models.Categories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesModel(
    val apiService: ApiServices, val dbServices: PharmacyDAO
) : CategoriesContract.Model {
    override fun getCategories(onFinishedListener: CategoriesContract.OnFinishedListener) {
        apiService.getAllCategories().enqueue(
            object : Callback<Categories>{
                override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                    if(response.isSuccessful){
                        response.body()?.let { onFinishedListener.onSuccessCategories(it) }
                    } else {
                        onFinishedListener.onFailedCategories(
                            APIFailure(response.code(), response.message())
                        )
                    }
                }

                override fun onFailure(call: Call<Categories>, t: Throwable) {
                    onFinishedListener.onFailedCategories(
                        APIFailure(-1, t.message.toString())
                    )
                }

            }
        )
    }
}

