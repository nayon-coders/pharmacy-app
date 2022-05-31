package com.mypharmacybd.ui.main_activity.fragments.home.presenter

import android.util.Log
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.home.HomeContract
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.data_models.slider.SliderData

class HomePresenter(
    val model: HomeContract.Model
) : HomeContract.Presenter,
    HomeContract.OnFinishedListener {

    private lateinit var view: HomeContract.View

    override fun setView(view: HomeContract.View) {
        this.view = view
    }


    override fun getProduct() {
        view.showProgressBar()
        model.getAllCategories(this)
        model.getAllProducts(this)
    }

    override fun onSuccessGetCategories(categories: Categories) {
        view.setDataToMainRecyclerView(categories)
        model.getSlider(this)

    }



    override fun onFailureGetCategories(apiFailure: APIFailure) {
        view.hideProgressBar()
    }

    override fun onSuccessGetAllProducts(products: Products) {
        Log.d(TAG, "onSuccessGetAllProducts: All Product Retrieve Successfully.")
        Log.d(TAG, "onSuccessGetAllProducts: Product Size = ${products.data.size}.")
    }

    override fun onFailureGetAllProducts(apiFailure: APIFailure) {
        Log.d(
            TAG,
            "onFailureGetAllProducts: code = ${apiFailure.statusCode} \nmessage = ${apiFailure.message}"
        )
    }

    /**
     *
     */
    fun readyHomeCategories(categories: Categories): Categories {
        val homeCategories: Categories = Categories()

        for (category in categories.data) {
            if (category.home_view == "1") homeCategories.data.toMutableList().add(category)
        }
        return homeCategories
    }

    override fun onSuccessSlider(sliderData: SliderData) {
        view.setSliderData(sliderData)
        view.hideProgressBar()
    }

    override fun onFailureSlider(apiFailure: APIFailure) {

    }

    companion object {
        private const val TAG = "HomePresenter"
    }
}