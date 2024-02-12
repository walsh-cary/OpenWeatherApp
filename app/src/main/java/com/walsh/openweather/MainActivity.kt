package com.walsh.openweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.walsh.openweather.repository.WeatherRepository
import com.walsh.openweather.repository.WeatherRepositoryImpl
import com.walsh.openweather.ui.theme.OpenWeatherAppTheme
import com.walsh.openweather.view.DisplayWeatherDetails
import com.walsh.openweather.viewmodel.WeatherViewModel
import com.walsh.openweather.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    private val repository: WeatherRepository by lazy {
        WeatherRepositoryImpl()
    }

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenWeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayWeatherDetails(viewModel)
                }
            }
        }
    }
}