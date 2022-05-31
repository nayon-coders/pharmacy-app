package com.mypharmacybd.ui.main_activity.fragments.home

import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Category
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.data_models.slider.SliderData

interface HomeContract {

    interface View{
        fun setDataToMainRecyclerView(categories: Categories)

        fun viewProductDetails(product: Product)

        fun showProgressBar()
        fun hideProgressBar()

        fun onViewDetailsClicked(category: Category)

        fun setSliderData(sliderData: SliderData)
    }

    interface Presenter{
        fun setView(view:View)
        fun getProduct()
    }

    interface Model{
        fun getAllCategories(onFinishedListener: OnFinishedListener)
        fun getAllProducts(onFinishedListener: OnFinishedListener)
        fun getSlider(onFinishedListener: OnFinishedListener)
    }

    interface OnFinishedListener{
        fun onSuccessGetCategories(categories: Categories)
        fun onFailureGetCategories(apiFailure: APIFailure)

        fun onSuccessGetAllProducts(products: Products)
        fun onFailureGetAllProducts(apiFailure: APIFailure)

        fun onSuccessSlider(sliderData: SliderData)
        fun onFailureSlider(apiFailure: APIFailure)
    }
}