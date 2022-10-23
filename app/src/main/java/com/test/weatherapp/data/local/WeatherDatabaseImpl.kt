package com.test.weatherapp.data.local

import com.test.weatherapp.data.models.weathermodels.WeatherParent
import javax.inject.Inject

class WeatherDatabaseImpl @Inject constructor(private val weatherDao: WeatherDao) :
    WeatherDatabase {
    override suspend fun getCityWeather(cityName: String): WeatherParent? =
        weatherDao.getWeatherOfCity(cityName)

    override suspend fun insertCityWeather(weatherParent: WeatherParent) =
        weatherDao.insertWeatherOfCity(weatherParent)

    override suspend fun getFavouriteCities(): List<WeatherParent> = weatherDao.getFavouriteCities()
    override suspend fun addFavouriteCity(weather: WeatherParent) = weatherDao.update(weather)

}