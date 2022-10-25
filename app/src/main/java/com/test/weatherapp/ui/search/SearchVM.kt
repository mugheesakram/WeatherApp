package com.test.weatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.remote.client.ApiResponse
import com.test.weatherapp.data.repo.DataRepository
import com.test.weatherapp.ui.base.BaseViewModel
import com.test.weatherapp.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(private val dataRepository: DataRepository) : BaseViewModel() {
    private val _enteredCityName: MutableLiveData<String> = MutableLiveData("")
    val enteredCityName: LiveData<String> = _enteredCityName

    private val _cityData = MutableLiveData<WeatherParent>()
    val cityData: LiveData<WeatherParent> = _cityData

    fun getWeatherByCityName(cityName: String) {
        launch {
            uiState.postValue(UIState.Loading)
            when (val response = dataRepository.getCityWeatherWithName(cityName, false)) {
                is ApiResponse.Success -> {
                    uiState.postValue(UIState.HideLoading)
                    _cityData.postValue(response.data)
                }
                is ApiResponse.Error -> {
                    uiState.postValue(
                        UIState.Error(
                            response.error.errorMessage, response.error.httpCode
                        )
                    )
                }
            }
        }
    }

    fun setCityName(name: String) {
        _enteredCityName.value = name
    }

    fun addToFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.postValue(UIState.Loading)
            _cityData.value?.favourite = 1
            _cityData.value?.let { dataRepository.addFavouriteCity(it) }
            uiState.postValue(UIState.HideLoading)
        }
    }
}