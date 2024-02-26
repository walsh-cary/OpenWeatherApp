package com.walsh.openweather.network

import com.walsh.openweather.data.OpenWeatherResponseModel
import com.walsh.openweather.network.NetworkConstants.API_ENDPOINT
import com.walsh.openweather.network.NetworkConstants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET(API_ENDPOINT)
    suspend fun getWeatherDetails(
        @Query("q") location: String? = null,    // for US cities: "Orlando,fl,us"
        @Query("APPID") appId: String = API_KEY,
    ): Response<OpenWeatherResponseModel>
}