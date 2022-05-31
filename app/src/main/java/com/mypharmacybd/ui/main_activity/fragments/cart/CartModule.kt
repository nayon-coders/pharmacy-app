package com.mypharmacybd.ui.main_activity.fragments.cart

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.ui.main_activity.fragments.cart.model.CartModel
import com.mypharmacybd.ui.main_activity.fragments.cart.presenter.CartPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class CartModule {

    @Provides
    @FragmentScoped
    fun provideCartPresenter(model:CartContract.Model)  : CartContract.Presenter {
        return CartPresenter(model = model)
    }

    @Provides
    @FragmentScoped
    fun provideCartModel(dbService: PharmacyDAO) : CartContract.Model {
        return CartModel(dbService)
    }
}