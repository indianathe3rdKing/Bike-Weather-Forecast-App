package com.example.bikeweatherforecastapp.data.repository

import com.example.bikeweatherforecastapp.BuildConfig
import com.example.bikeweatherforecastapp.data.remote.WeatherApiService
import com.example.bikeweatherforecastapp.domain.model.Coordinates
import com.example.bikeweatherforecastapp.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val apiService: WeatherApiService
) : SearchRepository{
    override suspend fun searchCity(city: String): Coordinates? {
        return try {
            val  response= apiService.getGeoLocation(city=city, apiKey = BuildConfig.API_KEY)

            val firstResult= response.firstOrNull()

            firstResult?.let{
                Coordinates(
                    lat = it.lat,
                    lon=it.lon
                )
            }
        }catch (e: Exception){
            null
        }
    }
}