package com.mypharmacybd.ui.main_activity.fragments.categories.presenter

import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.categories.CategoriesContract
import com.mypharmacybd.data_models.Categories

class CategoriesPresenter(val model: CategoriesContract.Model)
    : CategoriesContract.Presenter, CategoriesContract.OnFinishedListener {

    private lateinit var view : CategoriesContract.View

    override fun setView(view: CategoriesContract.View) {
        this.view = view
    }

    override fun getCategories() {
        view.showProgressbar()
        model.getCategories(this)
    }

    override fun onSuccessCategories(categories: Categories) {
        view.setDataToView(categories)
        view.hideProgressbar()
    }

    override fun onFailedCategories(apiFailure: APIFailure) {
        view.hideProgressbar()
    }

}