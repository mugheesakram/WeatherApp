package com.test.weatherapp.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.weatherapp.data.models.weathermodels.WeatherParent
import com.test.weatherapp.data.repo.DataRepository
import com.test.weatherapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteVM @Inject constructor(
    private val dataRepository: DataRepository
) : BaseViewModel() {
    init {
        getFavouriteCities()
    }

    private val _favouriteCities = MutableLiveData<List<WeatherParent>>()
    val favouriteCities: LiveData<List<WeatherParent>> = _favouriteCities


    fun getFavouriteCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteCities.postValue(dataRepository.getFavouriteCities())
        }
    }
}