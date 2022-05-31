package com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.contact_module

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices

import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.model.OrderListItemSupportModel
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.orderlist_view_support.presenter.OrderListItemSupportPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
@Module
@InstallIn(FragmentComponent::class)
class OrderItemViewSupportModule {

    @FragmentScoped
    @Provides
    fun provideCategoriesPresenter(ordersModel: OrderListItemSupport.Model): OrderListItemSupport
    .Presenter {
        return OrderListItemSupportPresenter(ordersModel)
    }

    @FragmentScoped
    @Provides
    fun provideCategoriesModel(apiService: ApiServices, dbServices: PharmacyDAO): OrderListItemSupport
    .Model {
        return OrderListItemSupportModel(apiService, dbServices)
    }
}