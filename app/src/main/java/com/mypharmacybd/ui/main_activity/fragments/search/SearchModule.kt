package com.mypharmacybd.ui.main_activity.fragments.search

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.search.model.SearchModel
import com.mypharmacybd.ui.main_activity.fragments.search.presenter.SearchPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class SearchModule {

    @Provides
    @FragmentScoped
    fun provideSearchPresenter(model: SearchContract.Model): SearchContract.Presenter {
        return SearchPresenter(model)
    }

    @Provides
    @FragmentScoped
    fun provideSearchModel(
        apiServices: ApiServices,
        dbServices: PharmacyDAO
    ): SearchContract.Model {
        return SearchModel(apiServices, dbServices)
    }
}