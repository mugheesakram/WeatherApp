package com.test.weatherapp.data.local

import com.test.weatherapp.data.models.weathermodels.WeatherParent

interface WeatherDatabase {
    suspend fun getCityWeather(cityName: String): WeatherParent
    suspend fun insertCityWeather(weather: WeatherParent)
    suspend fun getFavouriteCities(): List<WeatherParent>
}