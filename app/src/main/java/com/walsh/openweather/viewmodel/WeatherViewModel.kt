package com.walsh.openweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.walsh.openweather.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
    private val _weatherResponseState = MutableStateFlow<UIState>(UIState.Empty)
    val weatherResponseState = _weatherResponseState.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchText = MutableStateFlow("Vero Beach")
    val searchText = _searchText.asStateFlow()

    init {
        fetchWeatherDetails()
    }

    fun fetchWeatherDetails() {
        viewModelScope.launch {
            repository.getWeatherDetails(_searchText.value).collect {
                _weatherResponseState.value = it
            }
        }
    }

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
    }
}

class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}