package com.example.bikeweatherforecastapp.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bikeweatherforecastapp.presentation.screens.WeatherScreen
import com.example.bikeweatherforecastapp.ui.theme.BikeWeatherForecastAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BikeWeatherForecastAppTheme {
                WeatherScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}