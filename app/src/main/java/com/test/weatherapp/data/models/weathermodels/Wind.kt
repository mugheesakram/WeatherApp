package com.test.weatherapp.data.models.weathermodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wind(
    val deg: Int? = null,
    val gust: Double? = null,
    val speed: Double? = null
) : Parcelable