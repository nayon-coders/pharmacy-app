package com.mypharmacybd.ui.main_activity.fragments.category_details.presenter

import android.util.Log
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.category_details.CategoryDetailsContract

class CategoryDetailsPresenter(
    val model: CategoryDetailsContract.Model
) : CategoryDetailsContract.Presenter,
    CategoryDetailsContract.OnFinishedListener {

    private lateinit var view: CategoryDetailsContract.View

    override fun setView(view: CategoryDetailsContract.View) {
        this.view = view
    }

    override fun getCategoryData(category: String) {
        model.triggerApi(this, category)
    }


    override fun onSuccessGetProduct(products: Products) {

            view.setDataToView(products)

            view.setNoProductVisibility(products.data.isEmpty())


        Log.d(TAG, "onSuccessGetProduct: data size = ${products.data.size}")
    }

    override fun onFailureGetProduct(apiFailure: APIFailure) {
        Log.d(TAG, "onFailureGetProduct: ${apiFailure.message}")
    }

    companion object{
        private const val TAG = "CategoryDetailsPresenter"
    }


}