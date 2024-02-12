package com.walsh.openweather.repository

import com.walsh.openweather.network.NetworkClient
import com.walsh.openweather.viewmodel.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl: WeatherRepository {
    override suspend fun getWeatherDetails(locationQuery: String?): Flow<UIState> {
        return flow {
            emit(UIState.Loading())

            val response = NetworkClient.api.getWeatherDetails(locationQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(UIState.Loading(false))
                    emit(UIState.Success(it))
                } ?: kotlin.run {
                    emit(UIState.Loading(false))
                    emit(UIState.Failure(response.message()))
                }
            }
            else {
                emit(UIState.Loading(false))
                emit(UIState.Failure(response.raw().toString()))
            }
        }
    }
}