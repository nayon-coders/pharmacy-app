package com.mypharmacybd.ui.main_activity.fragments.category_details

import com.mypharmacybd.data_models.Product
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.network.api.models.APIFailure

interface CategoryDetailsContract {
    interface View{

        fun setDataToView(products:Products)
        fun showProgressbar()
        fun hideProgressbar()

        fun setNoProductVisibility(isVisible:Boolean)
        fun onAddToCartClicked(product: Product)
    }

    interface Presenter{
        fun setView(view:View)
        fun getCategoryData(category: String)
    }

    interface Model{
        fun triggerApi(onFinishedListener: OnFinishedListener, category: String)

    }



    interface OnFinishedListener{
        fun onSuccessGetProduct(products: Products)

        fun onFailureGetProduct(apiFailure: APIFailure)

    }
}