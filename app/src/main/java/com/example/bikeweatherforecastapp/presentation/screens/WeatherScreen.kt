package com.example.bikeweatherforecastapp.presentation.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.bikeweatherforecastapp.presentation.components.MainTabNavigator
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
@Composable
fun WeatherScreen(
    modifier: Modifier,
    viewModel: WeatherViewModel=koinViewModel()
){
    val weatherState by viewModel.weatherState
    val locationPermissionGranted by viewModel.locationPermissionGranted
    val savedCity = viewModel.savedCity.collectAsState().value
    val useCurrentLocation = viewModel.useCurrentLocation.collectAsState().value
    var isInitialized by remember{ mutableStateOf(false)}

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Respect the useCurrentLocation setting
            if (useCurrentLocation) {
                viewModel.checkLocationPermission()
            } else if (savedCity.isNotEmpty()) {
                viewModel.searchCity(savedCity)
            } else {
                // No saved city, fall back to current location
                viewModel.checkLocationPermission()
            }
        }
        isInitialized = true
    }

    LaunchedEffect(Unit) {
        if (isInitialized) return@LaunchedEffect

        if (!locationPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            // Respect the useCurrentLocation setting
            if (useCurrentLocation) {
                viewModel.checkLocationPermission()
            } else if (savedCity.isNotEmpty()) {
                viewModel.searchCity(savedCity)
            } else {
                // No saved city, fall back to current location
                viewModel.checkLocationPermission()
            }
            isInitialized = true
        }
    }

    Box(
        modifier=modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors= listOf(
                        Color(0xFF0F172A), //Dark blue-ray
                        Color(0xFF1E293B), //Medium blue-ray
                        Color(0xFF334155) //Light blue-ray
                    )
                )
            )
    ){
        when{
            weatherState.isLoading && weatherState.weatherData==null->{
                LoadingScreen()
            }
            weatherState.error !=null ->{
                ErrorScreen(
                    error=weatherState.error!!,
                    onRetry= {
                        viewModel.checkLocationPermission()
                    }
                )
            }
            weatherState.weatherData != null ->{

                MainTabNavigator(
                    weatherData = weatherState.weatherData!!,
                    viewModel = viewModel
                )
            }
            else ->{
                WelcomeScreen()
            }
        }
    }
}