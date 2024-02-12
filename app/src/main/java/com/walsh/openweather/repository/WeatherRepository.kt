package com.walsh.openweather.repository

import com.walsh.openweather.viewmodel.UIState
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherDetails(locationQuery: String?): Flow<UIState>
}