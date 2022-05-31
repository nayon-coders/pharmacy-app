package com.mypharmacybd.ui.main_activity.fragments.account.presenter

import android.util.Log
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.account.AccountContract
import com.mypharmacybd.user.Session

class AccountPresenter(val model: AccountContract.Model) : AccountContract.Presenter,
    AccountContract.OnFinishedListener {

    private lateinit var _view: AccountContract.View
    private lateinit var _sessionEntity:SessionEntity

    override fun setView(view: AccountContract.View) {
        Log.d(TAG, "setView: is called")
        this._view = view
    }

    override fun getSession() {
        _view.showLoading()
        model.getSavedSession(this)
        Log.d(TAG, "getSession: is called")
    }

    override fun onSuccessGetSession(sessionEntity: SessionEntity) {
        Log.d(TAG, "onSuccessGetSession: is called")
        _sessionEntity = sessionEntity
        model.getUserDetailsFromAPI(sessionEntity.accessToken, this)
    }

    override fun onFailureGetSession() {
        Log.d(TAG, "onFailureGetSession: is called")
        _view.showLoginLayout()
        _view.hideLoading()

    }

    override fun onSuccessGetUserDetails(userResponse: UserResponse) {
        Log.d(TAG, "onSuccessGetUserDetails: is called")
        _view.hideLoading()
        _view.setUserDataToView(userResponse)
        Session.loginStatus = true
        Session.authToken = _sessionEntity.accessToken
        Session.userResponse = userResponse
    }

    override fun onFailureGetUserDetails(apiFailure: APIFailure) {
        _view.showLoginLayout()
        _view.hideLoading()
        model.clearSavedSession()
    }

    override fun onLogoutClicked() {
        model.clearSavedSession()
        _view.showLoginLayout()
        Session.clearSession()
    }

    companion object {
        private const val TAG = "AccountPresenter"
    }
}