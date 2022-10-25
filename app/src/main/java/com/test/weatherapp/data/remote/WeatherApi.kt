package com.test.weatherapp.data.remote

import com.test.weatherapp.data.models.locationmodels.LocationModelItem
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.client.ApiResponse

interface WeatherApi {
    suspend fun getWeatherForCity(lat: Double, long: Double): ApiResponse<WeatherParent>
    suspend fun getCoordinatesForCity(cityName: String): ApiResponse<List<LocationModelItem>>
}