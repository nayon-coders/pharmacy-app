package com.mypharmacybd.ui.main_activity.fragments.user_update_info

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.user_update_info.model.UpdateUserInfoModel
import com.mypharmacybd.ui.main_activity.fragments.user_update_info.presenter.UpdateUserInfoPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class UpdateUserInfoModule {

    @Provides
    @FragmentScoped
    fun provideUpdateInfoPresenter(model:UpdateInfoContract.Model):UpdateInfoContract.Presenter{
        return UpdateUserInfoPresenter(model)
    }

    @Provides
    @FragmentScoped
    fun provideUpdateInfoModel(dbService:PharmacyDAO, apiServices: ApiServices):UpdateInfoContract.Model{
        return UpdateUserInfoModel(dbService, apiServices)
    }
}