package com.walsh.openweather.di

import org.koin.dsl.module

val appModule = module {
    includes(
        repoModule,
        viewModelModule,
    )
}