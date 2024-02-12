package com.walsh.openweather.viewmodel

import com.walsh.openweather.model.OpenWeatherResponseModel

sealed class UIState {
    data class Success(val data: OpenWeatherResponseModel): UIState()
    data class Failure(val error: String): UIState()
    data class Loading(val isLoading: Boolean = true): UIState()
    object Empty: UIState()
}