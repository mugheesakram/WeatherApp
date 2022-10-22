package com.test.weatherapp.data.client

import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseRepository : IRepository {
    override suspend fun <T : BaseResponse> executeSafely(call: suspend () -> Response<T>): ApiResponse<T> {
        try {
            val response: Response<T> = call.invoke()
            if (response.isSuccessful) {
                return ApiResponse.Success(response.code(), response.body()!!)
            }

            // Check if this is not a server side error (4** or 5**) then return error instead of success


            return ApiResponse.Error(
                ApiError(
                    httpCode = response.code(), errorMessage = response.errorBody().toString()
                )
            )

        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException -> {
                    exception.printStackTrace()
                    return ApiResponse.Error(
                        ApiError(
                            httpCode = 404, errorMessage = "Please check your internet connection"
                        )
                    )
                }
                else -> {
                    exception.printStackTrace()
                }
            }
            return ApiResponse.Error(
                ApiError(
                    errorMessage = exception.message.toString(),
                )
            )
        }
    }
}