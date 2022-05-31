package com.mypharmacybd.ui.main_activity.fragments.cart.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.ui.main_activity.fragments.cart.CartContract
import com.mypharmacybd.ui.main_activity.fragments.cart.adapter.CartAdapter
import com.mypharmacybd.user.Cart

class CartPresenter(val model: CartContract.Model) : CartContract.Presenter,
    CartContract.OnFinishedListener {
    private lateinit var view:CartContract.View

    override fun setView(view: CartContract.View) {
        this.view = view
    }

    override fun triggerCartProduct() {
        if (Cart.cartListLiveData != null) {
            view.setDataToView(allCartEntity = Cart.cartListLiveData!!)
        } else {
            model.checkCacheProduct(this)
        }
    }

    override fun updateCartItem(cartEntity: CartEntity, holder: CartAdapter.ViewHolder) {
        model.updateCart(cartEntity, holder)
    }

    override fun onProductCacheAvailable(allCartEntity: LiveData<List<CartEntity>>) {
        view.setDataToView(allCartEntity)
        view.visibleCartView()
    }

    override fun onProductCacheEmpty() {
        Log.d(TAG, "onProductCacheEmpty: Product in cache is Empty")
        view.hideCartView()
    }

    override fun removeCartItem(item: CartEntity) {
        model.deleteCartEntity(item, this)
    }

    override fun onSuccessItemDelete() {

    }

    override fun onFailureItemDelete() {

    }

    companion object {
        private const val TAG = "CartPresenter"
    }
}