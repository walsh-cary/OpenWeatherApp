package com.walsh.openweather.view

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.walsh.openweather.ext.toCelsius
import com.walsh.openweather.ext.toFahrenheit
import com.walsh.openweather.ext.toKilometer
import com.walsh.openweather.model.OpenWeatherResponseModel
import com.walsh.openweather.viewmodel.UIState
import com.walsh.openweather.viewmodel.WeatherViewModel

@Composable
fun DisplayWeatherDetails(viewModel: WeatherViewModel) {
    val uiState = viewModel.weatherResponseState.collectAsState().value
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState) {
            is UIState.Loading -> {
                CircularProgressIndicator()
            }

            is UIState.Success -> {
                DisplaySearch(viewModel, uiState.data)
            }

            is UIState.Failure -> {
                DisplayError("Bad request. Please try again.")
                viewModel.updateSearchText("Vero Beach")
                viewModel.fetchWeatherDetails()
            }

            is UIState.Empty -> {
                DisplayError("Unknown error. Please try again.")
                viewModel.updateSearchText("Vero Beach")
                viewModel.fetchWeatherDetails()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DisplaySearch(
    viewModel: WeatherViewModel,
    data: OpenWeatherResponseModel
) {
    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Scaffold(
        topBar = {
            SearchBar(
                query = searchText,
                onQueryChange = viewModel::updateSearchText,
                onSearch = {
                    viewModel.fetchWeatherDetails()
                },
                active = isSearching,
                onActiveChange = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                DisplaySuccess(weatherResponse = data)
            }
        }
    ) {

    }
}

@Composable
private fun DisplayError(error: String) {
    val context = LocalContext.current
    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
}

@Composable
private fun DisplaySuccess(weatherResponse: OpenWeatherResponseModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "City Name: ${weatherResponse.name} ",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Row {
            Text(
                text = "Latitude ${weatherResponse.coordinates?.lat ?: "unknown"} ",
                color = Color.Black,
                fontSize = 14.sp,
            )
            Text(
                text = "Longitude ${weatherResponse.coordinates?.long ?: "unknown"} ",
                color = Color.Black,
                fontSize = 14.sp,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = getImageUrl(weatherResponse.weatherData?.first()?.icon),
                contentDescription = "UI_Image_Weather_Icon",
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Magenta)
            )
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "${weatherResponse.main?.temp?.toInt()}K", fontSize = 20.sp)
                Text(
                    text = "${weatherResponse.main?.temp?.toCelsius()?.toInt()}\u00B0C",
                    fontSize = 20.sp
                )
                Text(
                    text = "${weatherResponse.main?.temp?.toFahrenheit()?.toInt()}\u00B0F",
                    fontSize = 20.sp
                )
            }
        }
        Row {
            Text(
                text = "Feels like ${
                    weatherResponse.main?.feelsLikeTemp?.toCelsius()?.toInt()
                }°C. "
            )
            Text(
                text = "${
                    weatherResponse.weatherData?.first()?.description.toString().replaceFirstChar {
                        it.uppercase()
                    }
                }."
            )
        }
        Row {
            Text(
                text = "Pressure: ${weatherResponse.main?.pressure} hPa",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Humidity: ${weatherResponse.main?.humidity}%",
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(text = "Visibility: ${weatherResponse.visibility?.toKilometer().toString()}")
    }
}

private fun getImageUrl(iconCode: String?): String =
    StringBuilder("https://openweathermap.org/img/wn/")
        .append(iconCode)
        .append("@4x.png")
        .toString()


