package com.example.bikeweatherforecastapp.domain.repository

import com.example.bikeweatherforecastapp.domain.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherForecast(lat: Double, lon: Double): Result<WeatherResponse>
}