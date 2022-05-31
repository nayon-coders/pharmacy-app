package com.mypharmacybd.ui.main_activity.fragments.account

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.account.model.AccountModel
import com.mypharmacybd.ui.main_activity.fragments.account.presenter.AccountPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class AccountModule {

    @Provides
    @FragmentScoped
    fun provideAccountPresenter(model: AccountContract.Model): AccountContract.Presenter {
        return AccountPresenter(model)
    }

    @Provides
    @FragmentScoped
    fun provideAccountModel(
        apiServices: ApiServices,
        database: PharmacyDAO
    ): AccountContract.Model {
        return AccountModel(apiServices, database)
    }
}