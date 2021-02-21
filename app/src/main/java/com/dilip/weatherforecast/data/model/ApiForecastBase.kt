package com.dilip.weatherforecast.data.model

import City
import Lista
import com.google.gson.annotations.SerializedName

data class ApiForecastBase(
    @SerializedName("cod") val cod : Int,
    @SerializedName("message") val message : Int,
    @SerializedName("cnt") val cnt : Int,
    @SerializedName("list") val list : List<Lista>,
    @SerializedName("city") val city : City)
