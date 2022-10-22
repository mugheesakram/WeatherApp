package com.test.weatherapp.data.models.weathermodels

data class WeatherParent(
    val city: City? = null,
    val cnt: Int? = null,
    val cod: String? = null,
    val list: List<WeatherPerDay>? = null,
    val message: Int? = null
)