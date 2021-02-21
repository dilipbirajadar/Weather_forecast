package com.dilip.weatherforecast.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dilip.weatherforecast.data.api.ApiHelper
import com.dilip.weatherforecast.viewmodels.ForecastListViewModel
//import com.dilip.weatherforecast.data.local.DatabaseHelper
import com.dilip.weatherforecast.viewmodels.WeatherViewModel

class ViewModelFactory(private val apiHelper: ApiHelper/*, private val dbHelper: DatabaseHelper*/) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){

            //return WeatherViewModel(apiHelper,dbHelper) as T
            return WeatherViewModel(apiHelper) as T
        }

        if(modelClass.isAssignableFrom(ForecastListViewModel::class.java)){

            //return WeatherViewModel(apiHelper,dbHelper) as T
            return ForecastListViewModel(apiHelper) as T
        }

        /*if (modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java)) {
            return SingleNetworkCallViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(SeriesNetworkCallsViewModel::class.java)) {
            return SeriesNetworkCallsViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(ParallelNetworkCallsViewModel::class.java)) {
            return ParallelNetworkCallsViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(RoomDBViewModel::class.java)) {
            return RoomDBViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(TimeoutViewModel::class.java)) {
            return TimeoutViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(TryCatchViewModel::class.java)) {
            return TryCatchViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(ExceptionHandlerViewModel::class.java)) {
            return ExceptionHandlerViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(LongRunningTaskViewModel::class.java)) {
            return LongRunningTaskViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(TwoLongRunningTasksViewModel::class.java)) {
            return TwoLongRunningTasksViewModel(apiHelper, dbHelper) as T
        }
        if (modelClass.isAssignableFrom(IgnoreErrorAndContinueViewModel::class.java)) {
            return IgnoreErrorAndContinueViewModel(apiHelper, dbHelper) as T
        }*/

        throw IllegalArgumentException("Unknown class name")
    }

}