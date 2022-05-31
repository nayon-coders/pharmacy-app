package com.mypharmacybd.ui.main_activity.model

import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.db.entity.SessionEntity
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.ui.main_activity.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(
    val apiServices: ApiServices,
    val dbServices: PharmacyDAO
) : MainContract.Model {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getCartProduct(onFinishedListener: MainContract.OnFinishedListener) {

        scope.launch {
            try {
                val allProductList = dbServices.getAllCartProduct()
                onFinishedListener.onSuccessGetCart(productList = allProductList)
            } catch (e: Exception) {
                onFinishedListener.onFailureGetCart(e)
            }
        }

    }

    override fun getSession(onFinishedListener: MainContract.OnFinishedListener) {
        scope.launch {
            val listSessionEntity = dbServices.getSession()

            if(listSessionEntity.isNotEmpty()){
                verifySession(listSessionEntity[0], onFinishedListener)
            }
        }
    }

    override fun verifySession(sessionEntity: SessionEntity, onFinishedListener: MainContract
    .OnFinishedListener) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + sessionEntity.accessToken

        apiServices.showUserData(headerMap = headerMap).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    response.body()?.let { onFinishedListener.onSessionVerified(sessionEntity, it) }
                } else {
                    onFinishedListener.onSessionNotVerified()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onFinishedListener.onSessionNotVerified()
            }
        })
    }
}