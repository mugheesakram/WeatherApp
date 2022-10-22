package com.test.weatherapp.data.models.weathermodels

data class WeatherPerDay(
    val clouds: Clouds? = null,
    val dt: Int? = null,
    val dt_txt: String? = null,
    val main: Main? = null,
    val pop: Int? = null,
    val sys: Sys? = null,
    val visibility: Int? = null,
    val weather: List<Weather>? = null,
    val wind: Wind
)