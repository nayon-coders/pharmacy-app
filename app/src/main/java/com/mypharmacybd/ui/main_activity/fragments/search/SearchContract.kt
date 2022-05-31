package com.mypharmacybd.ui.main_activity.fragments.search

import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.search.SearchResponse
import com.mypharmacybd.network.api.models.APIFailure

interface SearchContract {

    interface View{
        fun setSearchResponse(searchResponse: SearchResponse)
        fun onAddCartClicked(product: Product)
        fun showLoading()
        fun hideLoading()

    }

    interface Presenter{
        fun setView(view: View)
        fun onSearchClicked(searchString: String)
    }

    interface Model{
        fun searchProduct(searchString: String, onFinishedListener: OnFinishedListener)
    }

    interface OnFinishedListener{
        fun onSuccessSearchProduct(searchResponse: SearchResponse)
        fun onFailureSearchProduct(apiFailure: APIFailure)
    }

}