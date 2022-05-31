package com.mypharmacybd.ui.main_activity.fragments.categories

import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Category

interface CategoriesContract {

    interface View{
        fun showProgressbar()
        fun hideProgressbar()

        fun setDataToView(categories: Categories)

        fun onCategoryClicked(category: Category)
    }

    interface Model {
        fun getCategories(onFinishedListener: OnFinishedListener)
    }

    interface Presenter {
        fun setView(view:View)
        fun getCategories()
    }

    interface OnFinishedListener{
        fun onSuccessCategories(categories: Categories)
        fun onFailedCategories(apiFailure: APIFailure)
    }
}