package com.example.bikeweatherforecastapp.data.remote

import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat")lat: Double,
        @Query("lon")lon: Double,
        @Query("appId")apiKey: String,
        @Query("units")units: String="metric"
    ): WeatherResponse
}