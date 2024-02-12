package com.walsh.openweather.model

import com.google.gson.annotations.SerializedName

data class OpenWeatherResponseModel(
    @SerializedName("coord")
    val coordinates: LatLongCoordinatesModel? = null,
    @SerializedName("weather")
    val weatherData: List<WeatherDataModel?>? = null,
    @SerializedName("base")
    val base: String? = null,
    @SerializedName("main")
    val main: MainDataModel? = null,
    @SerializedName("visibility")
    val visibility: Int? = null,   // in meters
    @SerializedName("name")
    val name: String? = null,
)
