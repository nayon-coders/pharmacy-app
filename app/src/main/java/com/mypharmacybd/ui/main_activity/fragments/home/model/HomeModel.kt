package com.mypharmacybd.ui.main_activity.fragments.home.model

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.home.HomeContract
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.data_models.slider.SliderData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeModel(
    val apiServices: ApiServices,
    val dbService: PharmacyDAO
) : HomeContract.Model {
    override fun getAllCategories(onFinishedListener: HomeContract.OnFinishedListener) {
        apiServices.getHomeProducts().enqueue(
            object : Callback<Categories> {
                override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                    if (response.isSuccessful) {
                        response.body()?.let { onFinishedListener.onSuccessGetCategories(it) }
                    } else {
                        onFinishedListener.onFailureGetCategories(
                            apiFailure = APIFailure(
                                statusCode = response.code(),
                                message = response.message()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<Categories>, t: Throwable) {
                    onFinishedListener.onFailureGetCategories(
                        apiFailure = APIFailure(
                            statusCode = -1,
                            message = t.message.toString()
                        )
                    )
                }
            }
        )
    }

    override fun getAllProducts(onFinishedListener: HomeContract.OnFinishedListener) {
        apiServices.getAllProducts().enqueue(
            object : Callback<Products> {
                override fun onResponse(call: Call<Products>, response: Response<Products>) {
                    if (response.isSuccessful) {
                        response.body()?.let { onFinishedListener.onSuccessGetAllProducts(it) }
                    } else {
                        onFinishedListener.onFailureGetAllProducts(
                            APIFailure(response.code(), response.message())
                        )
                    }
                }

                override fun onFailure(call: Call<Products>, t: Throwable) {
                    onFinishedListener.onFailureGetAllProducts(
                        APIFailure(-1, t.message.toString())
                    )
                }
            }
        )
    }

    override fun getSlider(onFinishedListener: HomeContract.OnFinishedListener) {
        apiServices.getSliders().enqueue(object : Callback<SliderData> {
            override fun onResponse(call: Call<SliderData>, response: Response<SliderData>) {
                if (response.isSuccessful) {
                    response.body()?.let { onFinishedListener.onSuccessSlider(it) }

                } else {
                    onFinishedListener.onFailureSlider(
                        APIFailure(
                            statusCode = response.code(),
                            message = response.body().toString()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<SliderData>, t: Throwable) {
                onFinishedListener.onFailureSlider(
                    APIFailure(
                        statusCode = -1,
                        message = t.message.toString()
                    )
                )
            }
        })
    }
}


