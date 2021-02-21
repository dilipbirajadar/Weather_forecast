package com.dilip.weatherforecast.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilip.weatherforecast.data.api.ApiHelper
import com.dilip.weatherforecast.data.model.ApiForecastBase
//import com.dilip.weatherforecast.data.local.DatabaseHelper
import com.dilip.weatherforecast.data.model.ApiWeatherBase
import com.dilip.weatherforecast.util.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel (
    private val apiHelper: ApiHelper
    //private val dbHelper: DatabaseHelper
) : ViewModel(){
    private val weather = MutableLiveData<Resource<ApiWeatherBase>>()


    init {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            weather.postValue(Resource.loading(null))
            try {
                val weathersFromApi = apiHelper.getWeather()
                weather.postValue(Resource.success(weathersFromApi))
            }catch (e:Exception){
                weather.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getWeather(): LiveData<Resource<ApiWeatherBase>> {
        return weather
    }


}