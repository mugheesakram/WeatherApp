package com.test.weatherapp.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.weatherapp.CoroutineRule
import com.test.weatherapp.data.local.WeatherDatabase
import com.test.weatherapp.data.models.locationmodels.LocationModelItem
import com.test.weatherapp.data.models.weathermodels.City
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.WeatherApi
import com.test.weatherapp.data.remote.client.ApiResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class DataRepositoryImplTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: DataRepositoryImpl

    @Test
    fun ` when getCityWeatherWithName gets data from DB and is successful`(): Unit = runBlocking {
        //Given
        val local = mockk<WeatherDatabase> {
            coEvery { getCityWeather("Toronto") } returns WeatherParent(City(name = "Toronto"))
        }
        //When
        sut = DataRepositoryImpl(mockk(), local)
        val actual = sut.getCityWeatherWithName("Toronto", false)
        //Then
        Assert.assertEquals("Toronto", (actual as ApiResponse.Success).data.city.name)
        coVerify { local.getCityWeather("Toronto") }
    }

    @Test
    fun `when getCityWeatherWithName gets data from remote API and is successful`(): Unit =
        runBlocking {
            //Given
            val local = mockk<WeatherDatabase> {
                coEvery { getCityWeather("Manhattan") } returns null
                coEvery { insertCityWeather(WeatherParent(City(country = "America"))) } returns mockk()
            }
            val remote = mockk<WeatherApi> {
                coEvery { getCoordinatesForCity("Manhattan") } returns ApiResponse.Success(
                    200, listOf(
                        LocationModelItem("America", 1.00, 2.00)
                    )
                )
                coEvery { getWeatherForCity(1.00, 2.00) } returns ApiResponse.Success(
                    200, WeatherParent(City(country = "America"))
                )
            }
            //When
            sut = DataRepositoryImpl(remote, local)
            val actual = sut.getCityWeatherWithName("Manhattan", false)
            //Then
            Assert.assertEquals("America", (actual as ApiResponse.Success).data.city.country)
            coVerify { local.getCityWeather("Manhattan") }
            coVerify { remote.getCoordinatesForCity("Manhattan") }
            coVerify { remote.getWeatherForCity(1.00, 2.00) }
        }

    @Test
    fun `when getCoordinatesForCity has return empty data`(): Unit = runBlocking {
        //Given
        val local = mockk<WeatherDatabase> {
            coEvery { getCityWeather("Manhattan") } returns null
        }
        val remote = mockk<WeatherApi> {
            coEvery { getCoordinatesForCity("Manhattan") } returns ApiResponse.Success(
                200, listOf()
            )
        }
        //When
        sut = DataRepositoryImpl(remote, local)
        val actual = sut.getCityWeatherWithName("Manhattan", false)
        //Then
        Assert.assertEquals(
            "Not Such Location Found", (actual as ApiResponse.Error).error.errorMessage
        )
        coVerify { local.getCityWeather("Manhattan") }
        coVerify { remote.getCoordinatesForCity("Manhattan") }
    }

    @Test
    fun `When getFavouriteCities returns list of weatherParent Successfully`(): Unit = runBlocking {
        //Given
        val local = mockk<WeatherDatabase> {
            coEvery { getFavouriteCities() } returns listOf(
                WeatherParent(City(name = "London")), WeatherParent(City(name = "New York"))
            )
        }
        //When
        sut = DataRepositoryImpl(mockk(), local)
        val actual = sut.getFavouriteCities()
        //Then
        Assert.assertEquals("New York", actual[1].city.name)
    }
}