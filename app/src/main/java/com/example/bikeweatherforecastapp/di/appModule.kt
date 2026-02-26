package com.example.bikeweatherforecastapp.di

import com.example.bikeweatherforecastapp.data.local.DataStoreManager
import com.example.bikeweatherforecastapp.data.remote.Config
import com.example.bikeweatherforecastapp.data.remote.WeatherApiService
import com.example.bikeweatherforecastapp.data.repository.SearchRepositoryImpl
import com.example.bikeweatherforecastapp.data.repository.WeatherRepositoryImpl
import com.example.bikeweatherforecastapp.domain.repository.SearchRepository
import com.example.bikeweatherforecastapp.domain.repository.WeatherRepository
import com.example.bikeweatherforecastapp.domain.usecase.CalculateBikeRidingScoreUseCase
import com.example.bikeweatherforecastapp.domain.usecase.GetWeatherUseCase
import com.example.bikeweatherforecastapp.domain.usecase.SearchCityUseCase
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    // DataStore
    single { DataStoreManager(get()) }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.BASE_URL)
            .build()
    }

    single { get<Retrofit>().create(WeatherApiService::class.java) }

    single<WeatherRepository>{
        WeatherRepositoryImpl(get())
    }

    single{
        GetWeatherUseCase(get())
    }

    single {
        CalculateBikeRidingScoreUseCase()
    }

    viewModel{ WeatherViewModel(get(), get(), get(),get(),get()) }

    single<SearchRepository>{ SearchRepositoryImpl(get()) }

    factory{ SearchCityUseCase(get()) }
}