package com.mypharmacybd.ui.auth.fragments.user_login

import com.mypharmacybd.ui.auth.fragments.user_login.model.LoginModel
import com.mypharmacybd.ui.auth.fragments.user_login.presenter.LoginPresenter
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class LoginModule {

    @FragmentScoped
    @Provides
    fun providesPresenter(model: LoginContract.Model): LoginContract.Presenter {
        return LoginPresenter(model)
    }

    @FragmentScoped
    @Provides
    fun provideModel(
        apiServices: ApiServices,
        dbServices: PharmacyDAO
    ): LoginContract.Model {
        return LoginModel(apiServices, dbServices)
    }
}