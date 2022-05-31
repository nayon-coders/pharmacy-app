package com.mypharmacybd.ui.auth.fragments.user_registration
import com.mypharmacybd.ui.auth.fragments.user_registration.view.FragmentRegistration
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationData
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationResponse

interface RegistrationContract {
    interface OnFinishedListener {
        fun onSignupSuccess(response: RegistrationResponse)
        fun onSignupFailure(apiFailure: APIFailure)
    }

    interface Model {
        fun onDataReady(data: RegistrationData, onFinishedListener: OnFinishedListener)
        fun destroy()
    }

    interface Presenter{
        fun onSignUpButtonClicked(data: RegistrationData)
        fun setView(fragmentRegistration: FragmentRegistration)
        fun viewDestroyed()
    }

    interface View{
        fun showToast(message:String)
        fun openVeriFrag()
        fun showSuccess(name:String)
        fun showProgressBar()
        fun hideProgressBar()
    }

}