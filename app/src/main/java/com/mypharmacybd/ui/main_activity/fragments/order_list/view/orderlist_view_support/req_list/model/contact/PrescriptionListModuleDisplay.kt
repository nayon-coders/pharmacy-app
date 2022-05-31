package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.contact

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.categories.model.OrdersModel
import com.mypharmacybd.ui.main_activity.fragments.categories.presenter.OrderListPresenter
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.presenter.PrescriptionPresenter
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.req_list.model.working_models.PrescriptionsModel
import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.model.PrescriptionModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class PrescriptionListModuleDisplay {

    @FragmentScoped
    @Provides
    fun provideCategoriesPresenter(ordersModel: PrescriptionListContact.Model): PrescriptionListContact
    .Presenter {
        return PrescriptionPresenter(ordersModel)
    }

    @FragmentScoped
    @Provides
    fun provideCategoriesModel(apiService: ApiServices, dbServices: PharmacyDAO): PrescriptionListContact
    .Model {
        return PrescriptionsModel(apiService, dbServices)
    }
}