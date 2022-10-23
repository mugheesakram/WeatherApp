package com.test.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.weatherapp.data.models.weathermodels.WeatherParent

@Database(entities = [WeatherParent::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDB : RoomDatabase() {
    abstract fun WeatherDao(): WeatherDao
}