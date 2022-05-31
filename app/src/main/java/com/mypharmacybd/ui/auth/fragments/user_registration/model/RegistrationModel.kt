package com.mypharmacybd.ui.auth.fragments.user_registration.model

import android.util.Log
import com.mypharmacybd.ui.auth.fragments.user_registration.RegistrationContract
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationData
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegistrationModel(
    val apiServices: ApiServices
) : RegistrationContract.Model {

    override fun onDataReady(
        data: RegistrationData,
        onFinishedListener: RegistrationContract.OnFinishedListener
    ) {

        apiServices.userRegistration(ApiConfig.headerMap, data)
            .enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onDataReady: ${response.body().toString()}")
                        response.body()?.let { onFinishedListener.onSignupSuccess(it) }
                    } else {
                        val apiFailure = APIFailure(
                            response.code(),
                            response.message()
                        )
                        onFinishedListener.onSignupFailure(apiFailure)
                    }
                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    onFinishedListener.onSignupFailure(
                        APIFailure(-1, t.toString())
                    )
                }
            })
    }

    override fun destroy() {

    }

    companion object {
        const val TAG = "RegistrationModel"
    }
}