package com.walsh.openweather.model

import com.google.gson.annotations.SerializedName

data class LatLongCoordinatesModel(
    @SerializedName("lat")
    val lat: Float? = null,
    @SerializedName("lon")
    val long: Float? = null,
)
