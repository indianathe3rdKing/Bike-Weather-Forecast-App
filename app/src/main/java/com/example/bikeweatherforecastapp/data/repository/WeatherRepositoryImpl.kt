package com.example.bikeweatherforecastapp.data.repository

import com.example.bikeweatherforecastapp.data.remote.Config
import com.example.bikeweatherforecastapp.data.remote.WeatherApiService
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val apiService: WeatherApiService,
) : WeatherRepository{
    override suspend fun getWeatherForecast(
        lat: Double,
        lon: Double
    ): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeatherForecast(lat,lon, Config.OPENWEATHER_API_KEY)
            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}