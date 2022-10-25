package com.test.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.test.weatherapp.data.local.AppDB
import com.test.weatherapp.data.local.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideTrendyDao(appDatabase: AppDB): WeatherDao {
        return appDatabase.WeatherDao()
    }

    @Provides
    @Singleton
    fun provideAppDB(@ApplicationContext appContext: Context): AppDB {
        return Room.databaseBuilder(
            appContext,
            AppDB::class.java,
            "WeatherData"
        ).build()
    }

}