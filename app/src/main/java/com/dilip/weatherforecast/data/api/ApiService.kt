package com.dilip.weatherforecast.data.api

import com.dilip.weatherforecast.data.model.ApiForecastBase
import com.dilip.weatherforecast.data.model.ApiWeatherBase
import retrofit2.http.*

interface ApiService {

    @GET("weather?lat=18.589800&lon=73.743202&appid=a912e190758be13b25f007bcb2765611")
    //suspend fun getWeather(@QueryMap map: Map<String , String>): ApiWeatherBase
    suspend fun getWeather(): ApiWeatherBase

    @GET("forecast?lat=18.589800&lon=73.743202&appid=a912e190758be13b25f007bcb2765611")
    //suspend fun getForecast(@FieldMap mutableMapOf: String): List<ApiForecastBase>
    suspend fun getForecast(): ApiForecastBase
}