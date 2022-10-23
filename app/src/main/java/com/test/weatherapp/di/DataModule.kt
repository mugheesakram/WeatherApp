package com.test.weatherapp.di

import com.test.weatherapp.data.local.WeatherDatabase
import com.test.weatherapp.data.local.WeatherDatabaseImpl
import com.test.weatherapp.data.remote.WeatherApi
import com.test.weatherapp.data.remote.WeatherApiImpl
import com.test.weatherapp.data.repo.DataRepository
import com.test.weatherapp.data.repo.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideDataRepository(dataRepository: DataRepositoryImpl): DataRepository

    @Binds
    @Singleton
    abstract fun provideLocalRepository(localRepository: WeatherDatabaseImpl): WeatherDatabase

    @Binds
    @Singleton
    abstract fun provideRemoteRepository(remoteRepository: WeatherApiImpl): WeatherApi
}