package com.walsh.openweather.data

import com.google.gson.annotations.SerializedName

data class LatLongCoordinatesModel(
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val long: Double? = null,
)
