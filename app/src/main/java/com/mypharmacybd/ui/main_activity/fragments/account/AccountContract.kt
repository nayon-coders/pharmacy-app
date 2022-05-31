package com.mypharmacybd.ui.main_activity.fragments.account

import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.network.api.models.APIFailure

interface AccountContract {

    interface OnFinishedListener {
        fun onSuccessGetSession(sessionEntity:SessionEntity)
        fun onFailureGetSession()

        fun onSuccessGetUserDetails(userResponse: UserResponse)
        fun onFailureGetUserDetails(apiFailure: APIFailure)

    }

    interface View {
        fun setUserDataToView(userResponse: UserResponse)
        fun showLoginLayout()
        fun showProfileLayout()

        fun showLoading()
        fun hideLoading()
    }

    interface Model {
        fun getSavedSession(onFinishedListener: OnFinishedListener)
        fun clearSavedSession()
        fun getUserDetailsFromAPI(authorization:String, onFinishedListener: OnFinishedListener)
    }

    interface Presenter {
        fun setView(view: View)
        fun getSession()
        fun onLogoutClicked()
    }
}