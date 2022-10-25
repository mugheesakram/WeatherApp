package com.test.weatherapp.data.local

import androidx.room.*
import com.test.weatherapp.data.models.weathermodels.WeatherParent

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather_table WHERE city LIKE '%' || :cityName || '%'")
    suspend fun getWeatherOfCity(cityName: String): WeatherParent

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeatherOfCity(repos: WeatherParent)

    @Query("SELECT * FROM weather_table WHERE favourite = 1 ")
    suspend fun getFavouriteCities(): List<WeatherParent>

    @Update(entity = WeatherParent::class)
    fun update(obj: WeatherParent)
}