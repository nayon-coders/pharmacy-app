package com.mypharmacybd.ui.auth.fragments.user_login.presenter

import android.util.Log
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.ui.auth.fragments.user_login.LoginContract
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginCredentials
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginResponse
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.user.Session

class LoginPresenter(
    var model: LoginContract.Model

    ) : LoginContract.Presenter, LoginContract.OnFinishedLoginListener{

    private lateinit var view:LoginContract.View

    override fun setView(view: LoginContract.View) {
        this.view = view
    }

    override fun onLoginButtonClicked(credentials: LoginCredentials) {
        model.onDataReadyToLogin(credentials, this)
    }

    override fun onViewDestroyed() {
        model.destroyModel()
    }


    /*
    Methods for OnFinishedListener
     */
    override fun onSuccessLogin(loginResponse: LoginResponse) {
        Log.d(TAG, "onSuccessLogin: status code = ${loginResponse.accessToken}\nLogin Success")
        model.saveLoginSession(
            SessionEntity(
                userId = loginResponse.user?.id,
                name = loginResponse.user?.name ?: "",
                email = loginResponse.user?.email ?: "",
                slug = loginResponse.user?.slug ?: "",
                phone = loginResponse.user?.phone ?: "",
                accessToken = loginResponse.accessToken ?: ""
            ),
            this
        )
        Session.loginStatus = true
        Session.authToken = loginResponse.accessToken
    }

    override fun onFailureLogin(apiFailure: APIFailure) {
        if(apiFailure.statusCode > 0){
            view.onUsernamePasswordError()
        } else {
            view.showErrorDialog(apiFailure.message)
        }
    }

    override fun onSuccessSessionSave() {
        view.hideProgressbar()
        view.onSuccessLogin()
        Log.d(TAG, "onSuccessSessionSave: is called!")
    }

    override fun onFailureSessionSave() {
        view.showErrorDialog("Failed to saved user session")
    }

    companion object{
        private const val TAG = "LoginPresenter"
    }

}