package com.mypharmacybd.ui.main_activity.fragments.cart.model

import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.ui.main_activity.fragments.cart.CartContract
import com.mypharmacybd.ui.main_activity.fragments.cart.adapter.CartAdapter
import com.mypharmacybd.user.Cart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class CartModel(private val dbService: PharmacyDAO) : CartContract.Model {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun checkCacheProduct(onFinishedListener: CartContract.OnFinishedListener) {

        coroutineScope.launch {
            ensureActive()
            val allCart = dbService.getAllCartProduct()
                onFinishedListener.onProductCacheAvailable(allCart)
        }
    }

    override fun updateCart(cartEntity: CartEntity, holder: CartAdapter.ViewHolder) {
        coroutineScope.launch {
            dbService.updateCartEntity(cartEntity)
        }
    }

    override fun deleteCartEntity(
        entity: CartEntity,
        onFinishedListener: CartContract.OnFinishedListener
    ) {
        try {
            coroutineScope.launch {
                dbService.deleteCartEntity(entity)
                onFinishedListener.onSuccessItemDelete()
            }
        } catch (e:Exception){
            onFinishedListener.onFailureItemDelete()
        }
    }
}