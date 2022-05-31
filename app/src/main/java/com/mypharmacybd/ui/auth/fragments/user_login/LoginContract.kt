package com.mypharmacybd.ui.auth.fragments.user_login

import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginCredentials
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginResponse
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.network.api.models.APIFailure

interface LoginContract {

    interface View {
        fun showProgressbar()

        fun hideProgressbar()

        fun onSuccessLogin()

        fun onUsernamePasswordError()

        fun showErrorDialog(message:String)
    }

    interface Presenter {
        fun setView(view: View)

        fun onViewDestroyed()

        fun onLoginButtonClicked(credentials: LoginCredentials)
    }

    interface Model {
        fun onDataReadyToLogin(data:LoginCredentials, onFinishedLoginListener: OnFinishedLoginListener)

        fun saveLoginSession(sessionEntity:SessionEntity, onFinishedLoginListener: OnFinishedLoginListener)

        fun destroyModel()
    }

    interface OnFinishedLoginListener {
        fun onSuccessLogin(loginResponse: LoginResponse)

        fun onFailureLogin(apiFailure: APIFailure)

        fun onSuccessSessionSave()

        fun onFailureSessionSave()


    }

}