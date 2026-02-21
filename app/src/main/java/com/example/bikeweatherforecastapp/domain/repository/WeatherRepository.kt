package com.example.bikeweatherforecastapp.domain.repository

interface WeatherRepository {
    suspend fun getWeatherForecast(lat: Double, lon: Double): Result<WeatherRepository>
}