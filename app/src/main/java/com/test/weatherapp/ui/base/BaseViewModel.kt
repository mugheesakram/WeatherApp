package com.test.weatherapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val _uiState: MutableLiveData<UIState> = MutableLiveData()
    val uiState: MutableLiveData<UIState> = _uiState

    fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    fun changeUIState(uiState: UIState) {
        _uiState.value = uiState
    }
}