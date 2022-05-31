package com.mypharmacybd.ui.main_activity

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.model.MainModel
import com.mypharmacybd.ui.main_activity.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Scope

@Module
@InstallIn(ActivityComponent::class)
class MainModule {

    @Provides
    @ActivityScoped
    fun provideMainPresenter(model: MainContract.Model):MainContract.Presenter{
        return MainPresenter(model)
    }

    @Provides
    @ActivityScoped
    fun provideMainModel(apiServices: ApiServices, dbServices: PharmacyDAO):MainContract.Model{
        return MainModel(apiServices, dbServices)
    }
}