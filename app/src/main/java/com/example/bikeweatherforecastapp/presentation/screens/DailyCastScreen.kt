package com.example.bikeweatherforecastapp.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.bikeweatherforecastapp.presentation.components.HourlyCard
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import com.example.bikeweatherforecastapp.ui.theme.DarkBlue
import com.example.bikeweatherforecastapp.ui.theme.LightBlue
import com.example.bikeweatherforecastapp.ui.theme.MediumBlue
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary


@Composable
fun DailyCastScreen(viewModel: WeatherViewModel) {

    val hourlyScores by viewModel.hourlyScores
    val isMetric by viewModel.isMetric.collectAsState()

    // State for selected hour index
    var selectedHourIndex by remember { mutableIntStateOf(0) }

    BackHandler {
        viewModel.updateSelectedDay(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkBlue,
                        MediumBlue,
                        LightBlue
                    )
                )
            )

    ) {
        if (hourlyScores.isEmpty()) {
            Text(
                text = "No hourly data available",
                modifier = Modifier.padding(16.dp),
                color = TextPrimary
            )
        } else {
            // Get the selected forecast and score based on index
            val (selectedForecast, selectedScore) = hourlyScores[selectedHourIndex]
            // Extract just the forecasts from the hourly scores
            val hourlyForecasts = hourlyScores.map { it.first }

            HourlyCard(
                forecast = selectedForecast,
                score = selectedScore,
                hourlyForecasts = hourlyForecasts,
                selectedIndex = selectedHourIndex,
                isMetric = isMetric,
                onHourSelected = { index ->
                    selectedHourIndex = index
                }
            )
        }
    }
}