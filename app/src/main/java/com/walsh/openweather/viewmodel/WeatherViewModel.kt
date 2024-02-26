package com.walsh.openweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walsh.openweather.data.PreviousSearchDataStore
import com.walsh.openweather.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val dataStore: PreviousSearchDataStore,
): ViewModel() {
    private val _weatherResponseState = MutableStateFlow<UIState>(UIState.Empty)
    val weatherResponseState = _weatherResponseState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.getSearchToken.collect {
                _searchText.value = it
                fetchWeatherDetails()
            }
        }
    }

    fun fetchWeatherDetails() {
        viewModelScope.launch {
            repository.getWeatherDetails(_searchText.value).collect {
                _weatherResponseState.value = it
            }
            updateDataStore(_searchText.value)
        }
    }

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    private suspend fun updateDataStore(
        token: String,
    ) {
        dataStore.saveToken(token)
    }
}