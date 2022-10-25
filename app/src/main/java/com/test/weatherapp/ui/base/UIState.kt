package com.test.weatherapp.ui.base

sealed class UIState {
    object Loading : UIState()
    object HideLoading : UIState()
    data class Error(val error: String? = null, val code: Int) : UIState()
}