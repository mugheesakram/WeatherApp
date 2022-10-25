package com.test.weatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.client.ApiResponse
import com.test.weatherapp.data.repo.DataRepository
import com.test.weatherapp.ui.base.BaseViewModel
import com.test.weatherapp.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val dataRepository: DataRepository
) : BaseViewModel() {
    private val _cityData = MutableLiveData<WeatherParent>()
    val cityData: LiveData<WeatherParent> = _cityData

    fun getWeatherOfCurrentLocation(lat: Double, lon: Double) {
        _uiState.postValue(UIState.Loading)
        launch {
            when (val response = dataRepository.getCityWeatherWithLatLong(lat, lon)) {
                is ApiResponse.Success -> {
                    _cityData.postValue(response.data)
                    _uiState.postValue(UIState.HideLoading)
                }
                is ApiResponse.Error -> {
                    _uiState.postValue(
                        UIState.Error(
                            response.error.errorMessage,
                            response.error.httpCode
                        )
                    )
                }
            }
        }
    }
}