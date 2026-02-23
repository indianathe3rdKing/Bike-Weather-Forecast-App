package com.example.bikeweatherforecastapp.presentation.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.bikeweatherforecastapp.domain.usecase.CalculateBikeRidingScoreUseCase
import com.example.bikeweatherforecastapp.domain.usecase.GetWeatherUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.compose.runtime.State
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.example.bikeweatherforecastapp.domain.model.BikeRidingScore
import com.example.bikeweatherforecastapp.domain.model.DailyForecast
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.domain.model.WeatherState
import com.google.android.gms.location.Priority
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherViewModel(
    application: Application,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val calculateBikeRidingScoreUseCase: CalculateBikeRidingScoreUseCase,
) : AndroidViewModel(application) {
    //location
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(
            application
        )
    private val _locationPermissionGranted = mutableStateOf(false)
    val locationPermissionGranted: State<Boolean> = _locationPermissionGranted

    //Weather
    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    //score
    private val _dailyScores =
        mutableStateOf<List<Pair<DailyForecast, BikeRidingScore>>>(emptyList())
    val dailyScores: State<List<Pair<DailyForecast, BikeRidingScore>>> = _dailyScores

    fun checkLocationPermission() {
        val context = getApplication<Application>()
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        _locationPermissionGranted.value = hasPermission
        if (hasPermission) getCurrentLocation()
    }


    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun getCurrentLocation() {
        if (
            ContextCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            )

                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        fetchWeatherData(it.latitude, it.longitude)
                    }
                }

                .addOnFailureListener { exception ->
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        error = "Failed to get location : ${exception.message}"
                    )
                }
        }
    }

    private fun fetchWeatherData(latitude: Double, longitude: Double) {
        _weatherState.value = _weatherState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            getWeatherUseCase(latitude, longitude)
                .onSuccess { response ->
                    val dailyForecast = processForestIntoDaily(response)
                    val score = dailyForecast.map { forecast ->
                        forecast to calculateBikeRidingScoreUseCase(forecast)
                    }
                    _dailyScores.value = score
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        weatherData = response.copy(daily = dailyForecast),
                        error = null
                    )

                }
                .onFailure { exception ->
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        error = "Failed to fetch weather data: ${exception.message}"
                    )
                }
        }
    }

    private fun processForestIntoDaily(response: WeatherResponse): List<DailyForecast> {
        val allDayDailyForecasts = mutableListOf<DailyForecast>()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        //Group forecast items by date string (yyyy-MM-dd)
        val dailyGroups = response.list.groupBy {
            dateFormat.format(it.date * 1000)
        }

        dailyGroups.values.forEach { singleDayForecast ->
            if (singleDayForecast.isNotEmpty()) {
                val firstForecast = singleDayForecast.first()
                val maxTemp = singleDayForecast.minOf { it.main.tempMax }
                val minTemp = singleDayForecast.minOf {
                    it.main.tempMin
                }
                val avgHumidity = singleDayForecast.map { it.main.humidity }.average().toInt()
                val avgWindSpeed = singleDayForecast.map { it.wind.speed }
                val avgPrecipitation = singleDayForecast.map { it.precipitationPredictability
                }.average()

                //Get the most common weather condition for the day

                val mostCommonWeather = singleDayForecast
                    .flatMap { it.weather }
                    .groupBy { it.main }
                    .maxByOrNull { it.value.size }
                    ?.value?.first()?: firstForecast.weather.first()

                val dailyForecast= DailyForecast(
                    date= firstForecast.date,
                    temperature = Temperature(
                        day= firstForecast.main.temp,
                        min= minTemp,
                        max=maxTemp,
                        night=firstForecast.main.temp
                    ),
                    weather=listOf(mostCommonWeather),
                    humidity = avgHumidity,
                    windSpeed = avgWindSpeed,
                    precipitationPredictability = avgPrecipitation
                )
                allDayDailyForecasts.add(dailyForecast)
            }
        }
        return allDayDailyForecasts.take(6)
    }




}

