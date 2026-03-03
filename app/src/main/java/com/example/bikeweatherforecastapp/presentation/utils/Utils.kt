package com.example.bikeweatherforecastapp.presentation.utils


import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.bikeweatherforecastapp.data.remote.Config
import com.example.bikeweatherforecastapp.domain.model.Weather
import com.example.bikeweatherforecastapp.ui.theme.ScoreDangerous
import com.example.bikeweatherforecastapp.ui.theme.ScoreExcellent
import com.example.bikeweatherforecastapp.ui.theme.ScoreGood
import com.example.bikeweatherforecastapp.ui.theme.ScoreModerate
import com.example.bikeweatherforecastapp.ui.theme.ScorePoor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun getWeatherIconUrl(iconCode: String): String{
        return "${Config.WEATHER_ICON_BASE_URL}$iconCode@2x.png"
    }

    fun formatDate(timestamp: Long): String{
        val date = Date(timestamp * 1000) //Convert to milliseconds
        val dateFormat = SimpleDateFormat("EEE,MMM d", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun formatTime(timestamp: Long): String{
        val date = Date(timestamp * 1000)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(date)
    }

    fun getScoreColor(score: Int): Color {
        return when{
            score>= 80 -> ScoreExcellent         // Green- Excellent
            score>=60 -> ScoreGood               // Light Green - Good
            score>=40 -> ScoreModerate           // Yellow - Moderate
            score>=20 -> ScorePoor               // Red - Poor
            else -> ScoreDangerous               // Dark Red - Dangerous
        }
    }

    fun toFahrenheit(celsius: Double): Double {
        return (celsius * 9 / 5) + 32
    }

    // Weather icon based on weather ID (same logic as CalculateBikeRidingScoreUseCase)
    fun getWeatherIcon(weather: Weather?, time: String): String {
        val hour= time.substring(0,2).toInt()
        val day = hour in 6..18

        return when (weather?.id) {
            in 200..232 -> "⛈️"  // Thunderstorm
            in 300..321 -> "🌦️"  // Drizzle
            in 500..531 -> "🌧️"  // Rain
            in 600..622 -> "❄️"  // Snow
            in 700..781 -> "🌫️"  // Atmosphere (mist, fog, etc.)
            800 ->if (day)"🌞" else "🌚"          // Clear

            in 801..804 ->if(day) "☁️" else "☁️🌙"  // Clouds
            else -> "🌤️"        // Default
        }
    }
}

fun String.capitalize(): String{
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}