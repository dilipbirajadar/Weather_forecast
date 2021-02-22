package com.dilip.weatherforecast.extension

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun getKelVintoCelcius(temprature: Double):String{
    val tempInCel= temprature- 273.15
    return tempInCel.roundToInt().toString()+"°"
}

/*This method returns time in String format with pattern yyyy-MM-dd HH:mm:ss*/
fun getFormatTime(): String? {
    @SuppressLint("SimpleDateFormat") val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return dateFormat.format(Date(System.currentTimeMillis()))
}
