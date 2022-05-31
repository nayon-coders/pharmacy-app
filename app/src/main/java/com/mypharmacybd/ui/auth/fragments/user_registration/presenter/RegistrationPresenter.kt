package com.mypharmacybd.ui.auth.fragments.user_registration.presenter

import com.mypharmacybd.ui.auth.fragments.user_registration.RegistrationContract
import com.mypharmacybd.ui.auth.fragments.user_registration.view.FragmentRegistration
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationData
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationResponse


class RegistrationPresenter(
    var model: RegistrationContract.Model
) : RegistrationContract.Presenter, RegistrationContract.OnFinishedListener {
    private lateinit var view:FragmentRegistration

    override fun setView(fragmentRegistration: FragmentRegistration) {
        view = fragmentRegistration
    }

    override fun onSignUpButtonClicked(data: RegistrationData) {
        model.onDataReady(data, this)
        view.showProgressBar()
    }

    override fun onSignupSuccess(response: RegistrationResponse) {
        view.hideProgressBar()
        view.showSuccess(response.user?.name ?: "_")
    }

    override fun onSignupFailure(apiFailure: APIFailure) {
        view.hideProgressBar()
    }

    override fun viewDestroyed() {

    }

    companion object{
        const val TAG = "RegistrationPresenter"
    }
}