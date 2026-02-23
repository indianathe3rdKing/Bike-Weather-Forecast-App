package com.example.bikeweatherforecastapp.presentation.utils


import androidx.compose.ui.graphics.Color
import com.example.bikeweatherforecastapp.data.remote.Config
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

    fun getScoreColor(score: Int): Color {
        return when{
            score>= 80 -> Color(0xFF22C55E)// Green- Excellent
            score>=60 -> Color(0xFF4ADE80)// Light Green - Good
            score>=40 -> Color(0xFFFACC15) //Yellow - Moderate
            score>=20 -> Color(0xFFF87171) //Red - Poor
            else -> Color(0xFFDC2626)// Red - Dangerous

        }
    }

}

fun String.capitalize(): String{
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}