package com.mypharmacybd.ui.main_activity.presenter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.db.entity.CartEntity
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.ui.main_activity.MainContract.View
import com.mypharmacybd.ui.main_activity.MainContract.Model
import com.mypharmacybd.ui.main_activity.MainContract.Presenter
import com.mypharmacybd.ui.main_activity.MainContract.OnFinishedListener
import com.mypharmacybd.user.Session

class MainPresenter(val model:Model) : Presenter, OnFinishedListener {

    private lateinit var view:View

    override fun setView(view: View) {
        this.view = view
    }

    override fun getCart() {
        model.getCartProduct(this)
        Log.d(TAG, "getCart: is called")
    }

    override fun getSession() {
        model.getSession(this)
    }

    override fun onSuccessGetCart(productList: LiveData<List<CartEntity>>) {
        view.setCart(productList)
    }

    override fun onFailureGetCart(e: Exception) {
        Log.d(TAG, "onFailureGetCart: ${e.message}")
    }

    override fun onSessionVerified(sessionEntity: SessionEntity, userResponse: UserResponse) {
        view.setSession(sessionEntity, userResponse)
        Session.authToken = sessionEntity.accessToken
        Session.userResponse = userResponse
        Session.loginStatus = true

    }

    override fun onSessionNotVerified() {
        Log.d(TAG, "onSessionNotVerified: is called")
    }

    companion object{
        private const val TAG = "MainPresenter"
    }
}