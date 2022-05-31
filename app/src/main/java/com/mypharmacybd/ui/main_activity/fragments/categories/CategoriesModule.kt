package com.mypharmacybd.ui.main_activity.fragments.categories

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.categories.model.CategoriesModel
import com.mypharmacybd.ui.main_activity.fragments.categories.presenter.CategoriesPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class CategoriesModule {

    @FragmentScoped
    @Provides
    fun provideCategoriesPresenter(categoriesModel: CategoriesContract.Model): CategoriesContract
    .Presenter {
        return CategoriesPresenter(categoriesModel)
    }

    @FragmentScoped
    @Provides
    fun provideCategoriesModel(apiService: ApiServices, dbServices: PharmacyDAO): CategoriesContract
    .Model {
        return CategoriesModel(apiService, dbServices)
    }
}