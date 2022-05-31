package com.mypharmacybd.ui.auth.fragments.user_login.model

import android.util.Log
import com.mypharmacybd.ui.auth.fragments.user_login.LoginContract
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginCredentials
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginResponse
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginModel(
    val apiServices: ApiServices,
    private val dbService: PharmacyDAO
) : LoginContract.Model {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onDataReadyToLogin(
        data: LoginCredentials,
        onFinishedLoginListener: LoginContract.OnFinishedLoginListener
    ) {
        apiServices.userLogin(loginCredentials = data).enqueue(
            object : Callback<LoginResponse> {

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { onFinishedLoginListener.onSuccessLogin(it) }
                    } else {
                        onFinishedLoginListener.onFailureLogin(
                            apiFailure = APIFailure(
                                statusCode = response.code(),
                                message = response.message()
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    onFinishedLoginListener.onFailureLogin(
                        apiFailure = APIFailure(
                            statusCode = -1,
                            message = t.message.toString()
                        )
                    )
                }
            }
        )
    }


    override fun saveLoginSession(
        sessionEntity: SessionEntity,
        onFinishedLoginListener: LoginContract.OnFinishedLoginListener
    ) {
        Log.d(TAG, "saveLoginSession: is called")
        scope.launch {
            ensureActive()

                val l = dbService.insertSession(sessionEntity)
                onFinishedLoginListener.onSuccessSessionSave()
                Log.d(TAG, "saveLoginSession: $l")

        }
    }

    override fun destroyModel() {}


    companion object {
        private const val TAG = "LoginModel"
    }

}