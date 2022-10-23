package com.test.weatherapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.weatherapp.data.models.weathermodels.City
import com.test.weatherapp.data.models.weathermodels.WeatherPerDay

class DataConverter {
    @TypeConverter
    fun fromWeatherPerDay(weatherPerDays: List<WeatherPerDay?>?): String? {
        if (weatherPerDays == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<WeatherPerDay?>?>() {}.type
        return gson.toJson(weatherPerDays, type)
    }

    @TypeConverter
    fun toWeatherPerDay(jsonStrong: String?): List<WeatherPerDay>? {
        if (jsonStrong == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<WeatherPerDay>>() {}.type
        return gson.fromJson(jsonStrong, type)
    }

    @TypeConverter
    fun fromCity(city: City?): String? {
        if (city == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<City>() {}.type
        return gson.toJson(city, type)
    }

    @TypeConverter
    fun toCity(jsonStrong: String?): City? {
        if (jsonStrong == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<City>() {}.type
        return gson.fromJson(jsonStrong, type)
    }
}