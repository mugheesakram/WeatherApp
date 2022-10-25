package com.test.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Context.isNetworkAvailable(): Boolean {
    try {
        val connectivity =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivity.getNetworkCapabilities(connectivity.activeNetwork)?.run {
            return hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) || hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        }
        return false
    } catch (e: Exception) {
        return false
    }
}

fun Double.format(fracDigits: Int): String {
    val df = DecimalFormat()
    df.maximumFractionDigits = fracDigits
    return df.format(this)
}

fun Double.toCelsius(): String {
    return (this - 273.15).format(0)
}

fun String.serverFormatToWeekDayMonthDateAndTime(): String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    format.timeZone = TimeZone.getTimeZone("UTC")
    try {
        val date = format.parse(this)
        val dateFormat = SimpleDateFormat("EEE, MMM d HH:mm")
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return this
}
