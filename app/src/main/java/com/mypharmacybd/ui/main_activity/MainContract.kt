package com.mypharmacybd.ui.main_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.db.entity.SessionEntity
import kotlin.Exception

interface MainContract {

    interface OnFinishedListener{
        fun onSuccessGetCart(productList: LiveData<List<CartEntity>>)
        fun onFailureGetCart(e:Exception)

        fun onSessionVerified(sessionEntity: SessionEntity, userResponse: UserResponse)
        fun onSessionNotVerified()

    }

    interface View{
        fun setCart(listCartEntity: LiveData<List<CartEntity>>)
        fun setSession(sessionEntity: SessionEntity, userResponse: UserResponse)
    }

    interface Presenter{
        fun setView(view:View)
        fun getSession()
        fun getCart()
    }

    interface Model {
        fun getCartProduct(onFinishedListener: OnFinishedListener)
        fun getSession(onFinishedListener: OnFinishedListener)

        fun verifySession(sessionEntity: SessionEntity, onFinishedListener: OnFinishedListener)
    }


}