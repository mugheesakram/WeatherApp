package com.test.weatherapp.data.repo

import com.test.weatherapp.data.local.WeatherDatabase
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.WeatherApi
import com.test.weatherapp.data.remote.client.ApiError
import com.test.weatherapp.data.remote.client.ApiResponse
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val remote: WeatherApi, private val local: WeatherDatabase
) : DataRepository {
    override suspend fun getCityWeatherWithName(
        cityName: String, isRefresh: Boolean
    ): ApiResponse<WeatherParent> {
        val data = local.getCityWeather(cityName)
        return if (data != null && !isRefresh) {
            ApiResponse.Success(200, data)
        } else {
            when (val locationData = remote.getCoordinatesForCity(cityName)) {
                is ApiResponse.Success -> {
                    if (locationData.data.isEmpty())
                        ApiResponse.Error(ApiError(10001, "Not Such Location Found"))
                    else
                        getCityWeatherWithLatLong(
                            locationData.data[0].lat ?: 0.00,
                            locationData.data[0].lon ?: 0.0
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
                local.insertCityWeather(weatherData.data)
                weatherData
            }
            is ApiResponse.Error -> {
                weatherData
            }
        }
    }

    override suspend fun addFavouriteCity(weatherParent: WeatherParent) {
        local.addFavouriteCity(weatherParent)
    }

    override suspend fun getFavouriteCities() = local.getFavouriteCities()
}