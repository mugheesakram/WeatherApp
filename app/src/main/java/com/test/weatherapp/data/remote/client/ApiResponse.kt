package com.test.weatherapp.data.remote.client

sealed class ApiResponse<out T> {
    data class Success<out T >(val code: Int, val data: T) : ApiResponse<T>()
    data class Error(val error: ApiError) : ApiResponse<Nothing>()
}
