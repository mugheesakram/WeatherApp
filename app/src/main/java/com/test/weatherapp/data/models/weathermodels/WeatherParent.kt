package com.test.weatherapp.data.models.weathermodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherParent(
    val city: City? = null,
    val cnt: Int? = null,
    val cod: String? = null,
    val list: List<WeatherPerDay>? = null,
    val message: Int? = null,
    @Transient val favourite: Int? = 0
) {
    @PrimaryKey
    val id: Int? = city?.id
}