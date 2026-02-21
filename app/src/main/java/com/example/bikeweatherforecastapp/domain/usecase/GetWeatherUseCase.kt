package com.example.bikeweatherforecastapp.domain.usecase

import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double,lon: Double): Result<WeatherResponse>{
        return repository.getWeatherForecast(lat,lon)
    }
}