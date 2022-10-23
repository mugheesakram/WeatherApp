package com.test.weatherapp.data.models.weathermodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.weatherapp.data.client.BaseResponse

@Entity(tableName = "weather_table")
data class WeatherParent(
    val city: City? = null,
    val cnt: Int? = null,
    val cod: String? = null,
    val list: List<WeatherPerDay>? = null,
    val message: Int? = null,
    @Transient var favourite: Int? = 0
):BaseResponse() {
    @PrimaryKey
    val id: Int? = city?.id
}