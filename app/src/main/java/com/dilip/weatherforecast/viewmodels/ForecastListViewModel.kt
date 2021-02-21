package com.dilip.weatherforecast.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dilip.weatherforecast.data.api.ApiHelper
import com.dilip.weatherforecast.data.model.ApiForecastBase
import com.dilip.weatherforecast.util.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class ForecastListViewModel(
        private val apiHelper: ApiHelper
        //private val dbHelper: DatabaseHelper
) : ViewModel(){

    private val forecast = MutableLiveData<Resource<ApiForecastBase>>()


    init {
        fetchForecast()
    }

    private fun fetchForecast() {
        viewModelScope.launch {
            forecast.postValue(Resource.loading(null))
            try {
                val forecastFromApi = apiHelper.getForecast()
                forecast.postValue(Resource.success(forecastFromApi))
            }catch (e: Exception){
                forecast.postValue(Resource.error(e.toString(),null))
            }
        }
    }

    fun getForeCast(): LiveData<Resource<ApiForecastBase>> {
        return forecast
    }
}