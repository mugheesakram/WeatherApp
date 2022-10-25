package com.test.weatherapp.ui.favourite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.weatherapp.CoroutineRule
import com.test.weatherapp.data.models.weathermodels.City
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.repo.DataRepository
import com.test.weatherapp.getOrAwaitValue
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
internal class FavouriteVMTest {
    @get:Rule
    var mainCoroutineRule = CoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: FavouriteVM

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when you get list of favourite cities successfully`() {
        //Given
        val repo = mockk<DataRepository> {
            coEvery { getFavouriteCities() } returns listOf(
                WeatherParent(City(name = "London")), WeatherParent(City(name = "New York"))
            )
        }
        //When
        sut = FavouriteVM(repo)
        sut.getFavouriteCities()
        //Then
        Assert.assertEquals("New York", sut.favouriteCities.getOrAwaitValue()[1].city.name)
        coVerify { repo.getFavouriteCities() }
    }
}