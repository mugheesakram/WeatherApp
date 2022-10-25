package com.test.weatherapp.data.remote

import com.test.weatherapp.data.remote.client.BaseRepository
import javax.inject.Inject

class WeatherApiImpl @Inject constructor(private val weatherService: WeatherService) :
    BaseRepository(), WeatherApi {
    override suspend fun getWeatherForCity(lat: Double, long: Double) = executeSafely {
        weatherService.getWeatherForCity(lat, long, "20")
    }

    override suspend fun getCoordinatesForCity(cityName: String) = executeSafely {
        weatherService.getCoordinatesForCity(cityName, "1")
    }
}