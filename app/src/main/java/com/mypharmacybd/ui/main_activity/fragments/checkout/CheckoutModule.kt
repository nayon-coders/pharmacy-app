package com.mypharmacybd.ui.main_activity.fragments.checkout

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.checkout.model.CheckoutModel
import com.mypharmacybd.ui.main_activity.fragments.checkout.presenter.CheckoutPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class CheckoutModule {

    @Provides
    @FragmentScoped
    fun provideCheckoutPresenter(model: CheckoutContract.Model): CheckoutContract.Presenter {
        return CheckoutPresenter(model)
    }

    @Provides
    @FragmentScoped
    fun provideCheckoutModel(
        apiServices: ApiServices,
        dbServices: PharmacyDAO
    ): CheckoutContract.Model {
        return CheckoutModel(apiServices, dbServices)
    }

}