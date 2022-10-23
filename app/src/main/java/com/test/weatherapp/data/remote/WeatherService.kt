package com.test.weatherapp.data.remote

import com.test.weatherapp.data.models.locationmodels.LocationModel
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/forecast")
    suspend fun getWeatherForCity(
        @Query("lat") lat: Double, @Query("long") long: Double, @Query("cnt") cnt: String
    ): Response<WeatherParent>

    @GET("geo/1.0/direct")
    suspend fun getCoordinatesForCity(
        @Query("q") cityName: String,
        @Query("limit") limit: String
    ): Response<LocationModel>
}