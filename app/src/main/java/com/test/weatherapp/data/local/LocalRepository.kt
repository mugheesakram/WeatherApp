package com.test.weatherapp.data.local

import com.test.weatherapp.data.models.weathermodels.WeatherParent
import javax.inject.Inject

class WeatherDatabaseImpl @Inject constructor(private val weatherDao: WeatherDao) :
    WeatherDatabase {
    override suspend fun getCityWeather(cityName: String) = weatherDao.getWeatherOfCity(cityName)

    override suspend fun insertCityWeather(weatherParent: WeatherParent) =
        weatherDao.insertWeatherOfCity(weatherParent)

    override suspend fun getFavouriteCities(): List<WeatherParent>  = weatherDao.getFavouriteCities()
}