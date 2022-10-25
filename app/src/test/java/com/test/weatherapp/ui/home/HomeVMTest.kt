package com.test.weatherapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.weatherapp.CoroutineRule
import com.test.weatherapp.data.models.weathermodels.City
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.client.ApiError
import com.test.weatherapp.data.remote.client.ApiResponse
import com.test.weatherapp.data.repo.DataRepository
import com.test.weatherapp.ui.base.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeVMTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: HomeVM

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when getWeatherOfCurrentLocation API returns successful`() {
        //Given
        val repo = mockk<DataRepository> {
            coEvery { getCityWeatherWithLatLong(1.00, 2.00) } returns ApiResponse.Success(
                200, WeatherParent(City(name = "Dubai"))
            )
        }
        //When
        sut = HomeVM(repo)
        sut.getWeatherOfCurrentLocation(1.00, 2.00)
        //Then
        Assert.assertEquals("Dubai", sut.cityData.value?.city?.name)
        coVerify { repo.getCityWeatherWithLatLong(1.00, 2.00)  }
    }

    @Test
    fun `When getWeatherOfCurrentLocation API returns error`() {
        //Given
        val repo = mockk<DataRepository> {
            coEvery {
                getCityWeatherWithLatLong(
                    1.00, 2.00
                )
            } returns ApiResponse.Error(ApiError(401, "Unauthorized User"))
        }
        //When
        sut = HomeVM(repo)
        sut.getWeatherOfCurrentLocation(1.00, 2.00)
        //Then
        Assert.assertEquals(401, (sut.uiState.value as UIState.Error).code)
        coVerify { repo.getCityWeatherWithLatLong(1.00, 2.00)  }
    }
}
