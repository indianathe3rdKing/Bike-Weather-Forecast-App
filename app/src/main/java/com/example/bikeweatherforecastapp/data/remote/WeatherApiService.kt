package com.example.bikeweatherforecastapp.data.remote

import com.example.bikeweatherforecastapp.domain.model.GeoResponse
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat")lat: Double,
        @Query("lon")lon: Double,
        @Query("appId")apiKey: String,
        @Query("units")units: String="metric"
    ): WeatherResponse

    @GET("data/geo/1.0/direct")
    suspend fun getGeoLocation(
        @Query("q")city:String,
        @Query("limit")limit:Int=1,
        @Query("appId")apiKey: String
    ):List<GeoResponse>
}