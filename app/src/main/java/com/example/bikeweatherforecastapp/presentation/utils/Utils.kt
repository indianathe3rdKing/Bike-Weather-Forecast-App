package com.example.bikeweatherforecastapp.presentation.utils

import com.example.bikeweatherforecastapp.data.remote.Config
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {
    fun getWeatherIconUrl(iconCode: String): String{
        return "${Config.WEATHER_ICON_BASE_URL}$iconCode@2x.png"
    }

    fun formatDate(timestamp: Long): String{
        val date = Date(timestamp * 1000) //Cpnvert to milliseconds
        val dateFormat = SimpleDateFormat("EEE,MMM d", Locale.getDefault())
        return dateFormat.format(date)
    }
}