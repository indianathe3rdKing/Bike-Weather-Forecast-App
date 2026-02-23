package com.example.bikeweatherforecastapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikeweatherforecastapp.domain.model.BikeRidingScore
import com.example.bikeweatherforecastapp.domain.model.DailyForecast
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import com.example.bikeweatherforecastapp.presentation.utils.Utils

@Composable
fun HeaderSection(
    weatherData: WeatherResponse,
    bestForecast: DailyForecast?,
    bestScore: BikeRidingScore?,
    viewModel: WeatherViewModel
){
    Card(
        modifier = Modifier.fillMaxSize(),
        colors= CardDefaults.cardColors(
            containerColor = Color(0xFF1E2938).copy(alpha = 0.8f)
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text="🚵 Bike Riding Forest",
                color=Color.White
                , fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text="${weatherData.city.name}, ${weatherData.city.country}",
                color = Color(0xFFCBD5E1),
                fontSize = 16.sp
            )

            if (bestForecast!= null&&bestScore!=null){
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text="🥇Best day :",
                        color=Color(0xFF22CFFE),
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text= Utils.formatDate(bestForecast.date),
                        color=Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = bestScore.overallRating,
                        color = Color(0xFF94A3B8),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}