package com.walsh.openweather.di

import com.walsh.openweather.repository.WeatherRepository
import com.walsh.openweather.repository.WeatherRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repoModule = module {
    singleOf(::WeatherRepositoryImpl) { bind<WeatherRepository>() }
}