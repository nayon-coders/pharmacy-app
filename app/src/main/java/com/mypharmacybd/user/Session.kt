package com.mypharmacybd.user

import com.mypharmacybd.data_models.user.UserResponse

object Session {
    var loginStatus = false
    var authToken:String? = null
    var userResponse:UserResponse? = null

    fun clearSession(){
        loginStatus = false
        authToken = null
        userResponse = null
    }
}