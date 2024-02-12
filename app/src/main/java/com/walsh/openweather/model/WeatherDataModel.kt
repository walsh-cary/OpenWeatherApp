package com.walsh.openweather.model

import com.google.gson.annotations.SerializedName

data class WeatherDataModel(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("main")
    val main: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("icon")
    val icon: String? = null,
)
