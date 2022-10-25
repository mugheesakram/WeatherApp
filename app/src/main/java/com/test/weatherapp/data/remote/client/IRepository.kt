package com.test.weatherapp.data.remote.client

import retrofit2.Response

internal interface IRepository {
    suspend fun <T >  executeSafely(call: suspend () -> Response<T>): ApiResponse<T>
}
