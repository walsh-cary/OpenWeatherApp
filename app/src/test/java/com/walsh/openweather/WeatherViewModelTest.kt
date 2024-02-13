package com.walsh.openweather

import com.walsh.openweather.model.LatLongCoordinatesModel
import com.walsh.openweather.model.MainDataModel
import com.walsh.openweather.model.OpenWeatherResponseModel
import com.walsh.openweather.model.WeatherDataModel
import com.walsh.openweather.repository.WeatherRepository
import com.walsh.openweather.viewmodel.UIState
import com.walsh.openweather.viewmodel.WeatherViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import kotlin.test.assertEquals

class WeatherViewModelTest {
    private val repository = mockk<WeatherRepository>(relaxed = true)

    private val successCall = OpenWeatherResponseModel(
        name = "Vero Beach",
        coordinates = LatLongCoordinatesModel(
            27.6386,
            -80.3973,
        ),
        weatherData = listOf(
            WeatherDataModel(
                description = "scattered clouds",
                icon = "03n",
            )
        ),
        main = MainDataModel(
            temp = 296.84,
            feelsLikeTemp = 297.12,
            pressure = 1011,
            humidity = 71,
        ),
        visibility = 10000,
    )

    private val failCall = "error message"

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify correct data is presented when call is successful`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val sut = WeatherViewModel(repository)
        coEvery { repository.getWeatherDetails("Vero Beach") } returns flowOf(UIState.Success(data = successCall))
        sut.fetchWeatherDetails()
        assertEquals(UIState.Success::class.java, sut.weatherResponseState.value::class.java)
        assertEquals(UIState.Success(data = successCall), sut.weatherResponseState.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify failure state returns correct data`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val sut = WeatherViewModel(repository)
        coEvery { repository.getWeatherDetails("Vero Beach") } returns flowOf(UIState.Failure(error = failCall))
        sut.fetchWeatherDetails()
        assertEquals(UIState.Failure::class.java, sut.weatherResponseState.value::class.java)
        assertEquals(UIState.Failure(failCall), sut.weatherResponseState.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `verify empty default state`() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val sut = WeatherViewModel(repository)
        assertEquals(UIState.Empty::class.java, sut.weatherResponseState.value::class.java)
    }
}