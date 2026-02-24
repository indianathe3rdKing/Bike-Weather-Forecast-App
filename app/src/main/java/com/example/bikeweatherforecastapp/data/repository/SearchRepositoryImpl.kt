package com.example.bikeweatherforecastapp.data.repository

import android.util.Log
import com.example.bikeweatherforecastapp.BuildConfig
import com.example.bikeweatherforecastapp.data.remote.WeatherApiService
import com.example.bikeweatherforecastapp.domain.model.Coordinates
import com.example.bikeweatherforecastapp.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val apiService: WeatherApiService
) : SearchRepository{
    override suspend fun searchCity(city: String): Coordinates? {
        return try {
            Log.d("SearchRepositoryImpl", "Searching for city: $city")
            val  response= apiService.getGeoLocation(city=city, apiKey = BuildConfig.API_KEY)
            Log.d("SearchRepositoryImpl", "Geo API response: $response")

            val firstResult= response.firstOrNull()

            firstResult?.let{
                Coordinates(
                    lat = it.lat,
                    lon=it.lon
                )
            }
        }catch (e: Exception){
            Log.e("SearchRepositoryImpl", "Error searching city: ${e.message}", e)
            null
        }
    }
}