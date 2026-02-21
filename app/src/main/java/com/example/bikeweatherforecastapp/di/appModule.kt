package com.example.bikeweatherforecastapp.di

import com.example.bikeweatherforecastapp.data.remote.Config
import com.example.bikeweatherforecastapp.data.repository.WeatherRepositoryImpl
import com.example.bikeweatherforecastapp.domain.repository.WeatherRepository
import com.example.bikeweatherforecastapp.domain.usecase.CalculateBikeRidingScoreUseCase
import com.example.bikeweatherforecastapp.domain.usecase.GetWeatherUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.BASE_URL)
            .build()
    }

    single { get<Retrofit>().create(WeatherRepository::class.java) }

    single<WeatherRepository>{
        WeatherRepositoryImpl(get())
    }

    single{
        GetWeatherUseCase(get())
    }

    single {
        CalculateBikeRidingScoreUseCase()
    }

    single { GetWeatherUseCase(get()) }

    viewModel{WeatherViewModel(get(),get(),get())}
}