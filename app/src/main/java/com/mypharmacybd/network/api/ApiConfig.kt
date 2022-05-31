package com.mypharmacybd.network.api

object ApiConfig {
    const val AUTHORIZATION = "Authorization"

    const val AUTH_PREFIX = "Bearer "

    // base url hide for all clients
    private const val liveBaseUrl = "https://apps.piit.us/my-pharmacy/api/v1/"

    // base url reference hide from outside this package
    internal const val BASE_URL = liveBaseUrl

    const val webBaseUrl = "https://apps.piit.us/my-pharmacy/"

    val headerMap:MutableMap<String, String> = mutableMapOf(
        "Accept" to "application/json",
        "Content-Type" to "application/json; charset=UTF-8",
        "X-Header-Token" to "base64:KWyE5YqjEnsf0L+9R7unn5QimC8eTW21sm1WalIA2+Y="
    )
}