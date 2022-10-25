package com.test.weatherapp.data.models.weathermodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherPerDay(
    val clouds: Clouds? = null,
    val dt: Int? = null,
    val dt_txt: String? = null,
    val main: Main? = null,
    val pop: Double? = null,
    val sys: Sys? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind
) : Parcelable