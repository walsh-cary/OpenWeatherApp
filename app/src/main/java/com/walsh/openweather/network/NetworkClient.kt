package com.walsh.openweather.network

import com.walsh.openweather.network.NetworkConstants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkClient {
    private var interceptor: HttpLoggingInterceptor? = null
    private var client: OkHttpClient? = null

    val api: OpenWeatherApi by lazy {
        initRetrofit(BASE_URL)
    }

    private fun initRetrofit(
        baseUrl: String
    ): OpenWeatherApi {
        interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        client = OkHttpClient.Builder().apply {
            interceptor?.let { this.addInterceptor(it) }
        }.build()

        client?.let {
            return getApi(baseUrl, it)
        } ?: throw Exception("Failed to create Network client")
    }

    private fun getApi(
        baseUrl: String,
        client: OkHttpClient,
    ): OpenWeatherApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(client)
        .build()
        .create(OpenWeatherApi::class.java)
}