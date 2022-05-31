package com.mypharmacybd.ui.main_activity.fragments.upload_prescription

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.model.PrescriptionModel
import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.presenter.PrescriptionPresenter

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class PrescriptionModule {

    @Provides
    @FragmentScoped
    fun providePrescriptionUploadInfoPresenter(model: PrescriptionContact.Model): PrescriptionContact.Presenter{
        return PrescriptionPresenter(model)
    }

    @Provides
    @FragmentScoped
    fun providePrescriptionUploadInfoModel(dbService:PharmacyDAO, apiServices: ApiServices):PrescriptionContact.Model{
        return PrescriptionModel(dbService, apiServices)
    }
}