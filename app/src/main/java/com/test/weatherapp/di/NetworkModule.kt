package com.test.weatherapp.di

import com.test.weatherapp.data.client.RetrofitNetwork
import com.test.weatherapp.data.remote.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesWeatherService(retrofitNetwork: RetrofitNetwork) =
        retrofitNetwork.createService(WeatherService::class.java)
}