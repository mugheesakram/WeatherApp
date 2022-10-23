package com.test.weatherapp.data.repo

import com.test.weatherapp.data.client.ApiResponse
import com.test.weatherapp.data.local.WeatherDatabase
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.WeatherApi
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val remote: WeatherApi, private val local: WeatherDatabase
) : DataRepository {
    override suspend fun getCityWeatherWithName(
        cityName: String, isRefresh: Boolean
    ): ApiResponse<WeatherParent> {
        val response: ApiResponse<WeatherParent>
        val data = local.getCityWeather(cityName)
        return if (data != null && !isRefresh) {
            ApiResponse.Success(200, data)
        } else {
            when (val locationData = remote.getCoordinatesForCity(cityName)) {
                is ApiResponse.Success -> {
                    getCityWeatherWithLatLong(
                        locationData.data.locationModel?.get(0)?.lat ?: 0.00,
                        locationData.data.locationModel?.get(0)?.lon ?: 0.0
                    )
                }
                is ApiResponse.Error -> {
                    locationData
                }
            }
        }
    }

    override suspend fun getCityWeatherWithLatLong(
        lat: Double,
        long: Double
    ): ApiResponse<WeatherParent> {
        return when (val weatherData = remote.getWeatherForCity(
            lat,
            long
        )) {
            is ApiResponse.Success -> {
                weatherData
            }
            is ApiResponse.Error -> {
                weatherData
            }
        }
    }
//
//    override suspend fun getCityName(lat: String, long: String): ApiResponse<LocationModel> {
//        TODO("Not yet implemented")
//    }

    override suspend fun addFavouriteCity(weatherParent: WeatherParent) {
        local.addFavouriteCity(weatherParent)
    }

    override suspend fun getFavouriteCities() = local.getFavouriteCities()
}