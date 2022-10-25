package com.test.weatherapp.data.repo

import com.test.weatherapp.data.remote.client.ApiResponse
import com.test.weatherapp.data.models.weathermodels.WeatherParent

interface DataRepository {
    suspend fun getCityWeatherWithName(
        cityName: String,
        isRefresh: Boolean
    ): ApiResponse<WeatherParent>

    suspend fun getCityWeatherWithLatLong(lat: Double, long: Double): ApiResponse<WeatherParent>

    suspend fun addFavouriteCity(weatherParent: WeatherParent)
    suspend fun getFavouriteCities(): List<WeatherParent>
}