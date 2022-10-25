package com.test.weatherapp.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.weatherapp.CoroutineRule
import com.test.weatherapp.data.models.weathermodels.City
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.client.ApiError
import com.test.weatherapp.data.remote.client.ApiResponse
import com.test.weatherapp.data.repo.DataRepository
import com.test.weatherapp.ui.base.UIState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class SearchVMTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: SearchVM

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when getWeatherByCityName API returns successful`() {
        //Given
        val repo = mockk<DataRepository> {
            coEvery { getCityWeatherWithName("London", false) } returns ApiResponse.Success(
                200, WeatherParent(City(name = "London"))
            )
        }
        //When
        sut = SearchVM(repo)
        sut.getWeatherByCityName("London")
        //Then
        Assert.assertEquals("London", sut.cityData.value?.city?.name)
        coVerify { repo.getCityWeatherWithName("London", false) }
    }

    @Test
    fun `when getWeatherByCityName API returns error`() {
        //Given
        val repo = mockk<DataRepository> {
            coEvery {
                getCityWeatherWithName("Amsterdam", false)
            } returns ApiResponse.Error(ApiError(404, "Not Found"))
        }
        //When
        sut = SearchVM(repo)
        sut.getWeatherByCityName("Amsterdam")
        //Then
        Assert.assertEquals(404, (sut.uiState.value as UIState.Error).code)
        coVerify { repo.getCityWeatherWithName("Amsterdam", false) }
    }
}
