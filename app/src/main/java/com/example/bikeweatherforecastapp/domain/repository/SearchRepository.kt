package com.example.bikeweatherforecastapp.domain.repository

import com.example.bikeweatherforecastapp.domain.model.Coordinates

interface SearchRepository {
    suspend fun searchCity(city: String): Coordinates?
}