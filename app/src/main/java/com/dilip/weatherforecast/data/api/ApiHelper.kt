package com.dilip.weatherforecast.data.api

import com.dilip.weatherforecast.data.model.ApiForecastBase
import com.dilip.weatherforecast.data.model.ApiWeatherBase

interface ApiHelper {

    suspend fun getWeather(): ApiWeatherBase

    suspend fun getForecast():ApiForecastBase
}