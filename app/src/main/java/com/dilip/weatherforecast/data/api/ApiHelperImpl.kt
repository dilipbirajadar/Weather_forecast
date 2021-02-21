package com.dilip.weatherforecast.data.api

class ApiHelperImpl(private val apiService: ApiService):ApiHelper {
    //var mutableMap = mutableMapOf<String,String>("lat" to "18.589800").put("lon","73.743202").plus("appid" to "a912e190758be13b25f007bcb2765611")
    //override suspend fun getWeather() = apiService.getWeather(mutableMap)

    override suspend fun getWeather() = apiService.getWeather()

    override suspend fun getForecast() = apiService.getForecast()
}