package com.mypharmacybd.network.api.models

data class APIFailure(
    val statusCode:Int,
    val message: String
)