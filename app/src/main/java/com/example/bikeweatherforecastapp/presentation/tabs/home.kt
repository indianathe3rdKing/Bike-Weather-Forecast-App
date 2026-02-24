package com.example.bikeweatherforecastapp.presentation.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.bikeweatherforecastapp.presentation.components.WeatherContent
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

object SelectTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel: WeatherViewModel = koinViewModel()
        val weatherState by viewModel.weatherState

        weatherState.weatherData?.let { data ->
            WeatherContent(data, viewModel)
        }
    }

}