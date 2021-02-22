package com.dilip.weatherforecast.util

import android.app.Application
import android.content.Context
import com.dilip.weatherforecast.util.MainApplication.Companion.applicationContext
import com.dilip.weatherforecast.util.MainApplication.Companion.instance

 class MainApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any
        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = MainApplication.applicationContext()
    }
}