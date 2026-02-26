package com.example.bikeweatherforecastapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


val Context.dataStoreManager by preferencesDataStore(name="user_preferences")

class DataStoreManager(private val context: Context){

    companion object{
        val CITY_KEY = stringPreferencesKey("city")
        val METRIC_KEY = booleanPreferencesKey("metric")

    }

    //Save metric to data store
    suspend fun setMetric(metric: Boolean) {
        context.dataStoreManager.edit {
            prefs-> prefs[METRIC_KEY] = metric
        }
    }

    //Save city to data store
    suspend fun setCity(city: String){
        context.dataStoreManager.edit { prefs->
            prefs[CITY_KEY] = city
        }
    }

    //Get city from data store
    val city: Flow<String> = context.dataStoreManager.data.map {
        prefs -> prefs[CITY_KEY] ?: ""
    }

    //Get city from data store
    val isMetric: Flow<Boolean> = context.dataStoreManager.data.map {
        prefs-> prefs[METRIC_KEY]?:true
    }

    // Get metric value synchronously (for initial state)
    fun getMetricSync(): Boolean = runBlocking {
        context.dataStoreManager.data.first()[METRIC_KEY] ?: true
    }

    fun getCitySync(): String = runBlocking {
        context.dataStoreManager.data.first()[CITY_KEY] ?:""
    }


}