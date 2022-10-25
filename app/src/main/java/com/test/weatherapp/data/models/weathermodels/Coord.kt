package com.test.weatherapp.data.models.weathermodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coord(
    val lat: Double? = null, val lon: Double? = null
) : Parcelable