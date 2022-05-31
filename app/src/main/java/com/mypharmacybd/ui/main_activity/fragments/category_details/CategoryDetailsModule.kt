package com.mypharmacybd.ui.main_activity.fragments.category_details

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.category_details.model.CategoryDetailsModel
import com.mypharmacybd.ui.main_activity.fragments.category_details.presenter.CategoryDetailsPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class CategoryDetailsModule {

    @Provides
    @FragmentScoped
    fun provideCategoryDetailsModel(apiServices: ApiServices, dbServices:PharmacyDAO)
    :CategoryDetailsContract.Model =
        CategoryDetailsModel(apiServices, dbServices)

    @Provides
    @FragmentScoped
    fun provideCategoryPresenter(model:CategoryDetailsContract.Model):CategoryDetailsContract.Presenter =
        CategoryDetailsPresenter(model)

}