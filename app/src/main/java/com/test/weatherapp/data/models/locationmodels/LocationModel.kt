package com.test.weatherapp.data.models.locationmodels

import com.test.weatherapp.data.client.BaseResponse

data class LocationModel(
    val locationModel: ArrayList<LocationModelItem>? = null
) : BaseResponse()