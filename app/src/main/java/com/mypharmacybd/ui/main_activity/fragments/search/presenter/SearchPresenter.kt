package com.mypharmacybd.ui.main_activity.fragments.search.presenter

import com.mypharmacybd.data_models.search.SearchResponse
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.search.SearchContract

class SearchPresenter(
    val model: SearchContract.Model
) : SearchContract.Presenter, SearchContract.OnFinishedListener {
    private var _view:SearchContract.View? = null

    override fun setView(view: SearchContract.View) {
        _view = view
    }

    override fun onSearchClicked(searchString: String) {
        _view?.showLoading()
        model.searchProduct(searchString, this)
    }

    override fun onSuccessSearchProduct(searchResponse: SearchResponse) {
        _view.let {
            it?.setSearchResponse(searchResponse)
        }
        _view?.hideLoading()
    }

    override fun onFailureSearchProduct(apiFailure: APIFailure) {
        _view?.hideLoading()
    }
}