package com.walsh.openweather.model

import com.google.gson.annotations.SerializedName

data class LatLongCoordinatesModel(
    @SerializedName("lat")
    val lat: Double? = null,
    @SerializedName("lon")
    val long: Double? = null,
)
