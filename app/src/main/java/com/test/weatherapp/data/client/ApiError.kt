package com.test.weatherapp.data.client

data class ApiError(
    var httpCode: Int = 0,
    var errorMessage: String
)