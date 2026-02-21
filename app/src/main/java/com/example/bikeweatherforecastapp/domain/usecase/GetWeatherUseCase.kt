package com.example.bikeweatherforecastapp.domain.usecase

import com.example.bikeweatherforecastapp.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double,lon: Double): Result<WeatherRepository>{
        return repository.getWeatherForecast(lat,lon)
    }
}