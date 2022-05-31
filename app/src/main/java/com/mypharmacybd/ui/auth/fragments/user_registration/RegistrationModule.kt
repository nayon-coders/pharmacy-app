package com.mypharmacybd.ui.auth.fragments.user_registration

import com.mypharmacybd.ui.auth.fragments.user_registration.model.RegistrationModel
import com.mypharmacybd.ui.auth.fragments.user_registration.presenter.RegistrationPresenter
import com.mypharmacybd.network.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class RegistrationModule {

    @FragmentScoped
    @Provides
    fun providesPresenter(
        model: RegistrationContract.Model
    ): RegistrationContract.Presenter {

        return RegistrationPresenter(model)
    }

    @FragmentScoped
    @Provides
    fun providesModel(
        apiServices: ApiServices
    ): RegistrationContract.Model {

        return RegistrationModel(apiServices)
    }
}