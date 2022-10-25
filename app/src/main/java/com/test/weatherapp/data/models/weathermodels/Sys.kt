package com.test.weatherapp.data.models.weathermodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sys(
    val pod: String? = null
) : Parcelable