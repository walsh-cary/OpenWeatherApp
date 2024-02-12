package com.walsh.openweather.model

import com.google.gson.annotations.SerializedName

// All temps in Kelvin
data class MainDataModel(
    @SerializedName("temp")
    val temp: Double? = null,
    @SerializedName("feels_like")
    val feelsLikeTemp: Double? = null,
    @SerializedName("temp_min")
    val tempMin: Double? = null,
    @SerializedName("temp_max")
    val tempMax: Double? = null,
    @SerializedName("pressure")
    val pressure: Int? = null,  // in hPA [hectopascals]
    @SerializedName("humidity")
    val humidity: Int? = null,
)
