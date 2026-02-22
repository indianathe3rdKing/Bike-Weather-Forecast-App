package com.example.bikeweatherforecastapp

import android.app.Application
import com.example.bikeweatherforecastapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class WeatherBikingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherBikingApp)
            modules(appModule)
        }
    }
}