package com.test.weatherapp.data.models.weathermodels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.test.weatherapp.data.remote.client.BaseResponse
import kotlinx.parcelize.Parcelize

@Entity(tableName = "weather_table")
@Parcelize
data class WeatherParent(
    @PrimaryKey
    @SerializedName("city")
    val city: City,
    val cnt: Int? = null,
    val cod: String? = null,
    val list: List<WeatherPerDay>? = null,
    val message: Int? = null,
    var favourite: Int? = 0
) : BaseResponse(), Parcelable