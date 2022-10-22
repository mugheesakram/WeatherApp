package com.test.weatherapp.data.remote

import com.test.weatherapp.data.client.ApiResponse
import com.test.weatherapp.data.models.locationmodels.LocationModel
import com.test.weatherapp.data.models.weathermodels.WeatherParent

interface WeatherApi {
    suspend fun getWeatherForCity(lat: String, long: String): ApiResponse<WeatherParent>
    suspend fun getCoordinatesForCity(cityName: String): ApiResponse<LocationModel>
}