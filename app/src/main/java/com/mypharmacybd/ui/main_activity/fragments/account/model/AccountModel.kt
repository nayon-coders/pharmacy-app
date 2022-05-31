package com.mypharmacybd.ui.main_activity.fragments.account.model

import android.util.Log
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.account.AccountContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountModel(val apiServices: ApiServices, val database: PharmacyDAO) :
    AccountContract.Model {

    private var scope = CoroutineScope(Dispatchers.IO)

    override fun getSavedSession(onFinishedListener: AccountContract.OnFinishedListener) {
        scope.launch {
            ensureActive()
            val sessionList = database.getSession()
            if(sessionList.isNotEmpty()){
                onFinishedListener.onSuccessGetSession(sessionList[0])
            } else {
                Log.d(TAG, "getSavedSession: session is null")
                onFinishedListener.onFailureGetSession()
            }
        }
    }

    override fun clearSavedSession() {
        scope.launch {
            ensureActive()
            database.deleteSession()
        }
    }

    override fun getUserDetailsFromAPI(
        authorization: String,
        onFinishedListener: AccountContract.OnFinishedListener
    ) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer $authorization"
        Log.d(TAG, "getUserDetailsFromAPI: auth data = $headerMap")
        apiServices.showUserData(headerMap = headerMap).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { onFinishedListener.onSuccessGetUserDetails(it) }

                    Log.d(TAG, "onResponse: response = ${response.body()}")
                } else {
                    onFinishedListener.onFailureGetUserDetails(
                        APIFailure(
                            response.code(),
                            response.message()
                        )
                    )
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onFinishedListener.onFailureGetUserDetails(
                    APIFailure(
                        -1,
                        t.message ?: ""
                    )
                )
            }

        })
    }
    
    companion object {
        private const val TAG = "AccountModel"
    }
}