package com.mypharmacybd.ui.main_activity.fragments.cart

import androidx.lifecycle.LiveData
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.ui.main_activity.fragments.cart.adapter.CartAdapter.ViewHolder

interface CartContract {

    interface View {
        fun setDataToView(allCartEntity: LiveData<List<CartEntity>>)
        fun visibleCartView()
        fun hideCartView()

        fun onCartUpdate(cartEntity: CartEntity, holder: ViewHolder)
        fun onRemoveItemClicked(item:CartEntity)

    }

    interface Presenter {
        fun setView(view: View)
        fun triggerCartProduct()
        fun updateCartItem(cartEntity: CartEntity, holder: ViewHolder)
        fun removeCartItem(item:CartEntity)
    }

    interface Model {
        fun checkCacheProduct(onFinishedListener: OnFinishedListener)
        fun updateCart(cartEntity: CartEntity, holder: ViewHolder)

        fun deleteCartEntity(entity: CartEntity, onFinishedListener: OnFinishedListener)
    }

    interface OnFinishedListener {
        fun onProductCacheAvailable(allCartEntity: LiveData<List<CartEntity>>)
        fun onProductCacheEmpty()

        fun onSuccessItemDelete()
        fun onFailureItemDelete()

    }
}