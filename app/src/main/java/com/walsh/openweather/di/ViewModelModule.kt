package com.walsh.openweather.di

import com.walsh.openweather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::WeatherViewModel)
}