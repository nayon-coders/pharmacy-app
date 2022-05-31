package com.mypharmacybd.ui.main_activity.fragments.order_list

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.fragments.categories.model.CategoriesModel
import com.mypharmacybd.ui.main_activity.fragments.categories.model.OrdersModel
import com.mypharmacybd.ui.main_activity.fragments.categories.presenter.OrderListPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class OrdersModule {

    @FragmentScoped
    @Provides
    fun provideCategoriesPresenter(ordersModel: OrderListContract.Model): OrderListContract
    .Presenter {
        return OrderListPresenter(ordersModel)
    }

    @FragmentScoped
    @Provides
    fun provideCategoriesModel(apiService: ApiServices, dbServices: PharmacyDAO): OrderListContract
    .Model {
        return OrdersModel(apiService, dbServices)
    }
}