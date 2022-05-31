package com.mypharmacybd.network.internet

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

object ConnectionChecker {
    // method for check internet connection enabled or not
    fun isConnected(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // api >= 23
            connectivityManager.activeNetwork != null
        } else  connectivityManager.activeNetworkInfo != null // api < 23
    }
}