package com.test.weatherapp.data.models

import com.test.weatherapp.data.remote.client.BaseResponse

data class ListResponse<T:Any>(
    val data: List<T>? = null
) : BaseResponse()