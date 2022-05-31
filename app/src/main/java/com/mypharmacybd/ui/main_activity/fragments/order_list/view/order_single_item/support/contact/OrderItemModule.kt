package com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.contact

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.categories.model.OrdersModel
import com.mypharmacybd.ui.main_activity.fragments.categories.presenter.OrderListPresenter
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.model.ItemOrderModel
import com.mypharmacybd.ui.main_activity.fragments.order_list.view.order_single_item.support.presenter.ItemOrderPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
@Module
@InstallIn(FragmentComponent::class)
class OrderItemModule {

        @FragmentScoped
        @Provides
        fun provideCategoriesPresenter(ordersModel: ItemOrderContact.Model): ItemOrderContact
        .Presenter {
            return ItemOrderPresenter(ordersModel)
        }

        @FragmentScoped
        @Provides
        fun provideCategoriesModel(apiService: ApiServices, dbServices: PharmacyDAO): ItemOrderContact
        .Model {
            return ItemOrderModel(apiService, dbServices)
        }

}