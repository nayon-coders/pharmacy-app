package com.mypharmacybd.ui.main_activity.fragments.home

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.home.model.HomeModel
import com.mypharmacybd.ui.main_activity.fragments.home.presenter.HomePresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class HomeModule {

    @FragmentScoped
    @Provides
    fun provideHomePresenter(model: HomeContract.Model): HomeContract.Presenter {
        return HomePresenter(model = model)
    }

    @FragmentScoped
    @Provides
    fun provideHomeModel(apiServices: ApiServices, dbService: PharmacyDAO): HomeContract.Model {
        return HomeModel(apiServices, dbService)
    }
}