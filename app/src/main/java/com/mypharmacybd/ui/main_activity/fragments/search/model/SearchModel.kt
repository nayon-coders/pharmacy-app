package com.mypharmacybd.ui.main_activity.fragments.search.model

import com.mypharmacybd.data_models.search.SearchResponse
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.search.SearchContract
import retrofit2.Call
import retrofit2.Response

class SearchModel(
    val apiServices: ApiServices,
    val dbServices: PharmacyDAO
) : SearchContract.Model {

    override fun searchProduct(
        searchString: String,
        onFinishedListener: SearchContract.OnFinishedListener
    ) {
        apiServices.searchByProductName(queryString = searchString).enqueue(object : retrofit2
        .Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { onFinishedListener.onSuccessSearchProduct(it) }
                } else {
                    onFinishedListener.onFailureSearchProduct(
                        APIFailure(response.code(), response.message())
                    )
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                onFinishedListener.onFailureSearchProduct(
                    APIFailure(-1, t.message.toString())
                )
            }
        }
        )
    }
}