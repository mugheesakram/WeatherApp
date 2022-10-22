package com.test.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isNetworkAvailable(): Boolean {
    try {
        val connectivity =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivity.getNetworkCapabilities(connectivity.activeNetwork)?.run {
            return hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        }
        return false
    } catch (e: Exception) {
        return false
    }
}
