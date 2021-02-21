package com.dilip.weatherforecast.adapter

import Lista
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.dilip.weatherforecast.R
import com.dilip.weatherforecast.extension.getKelVintoCelcius
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

class ForecastAdapter(private var items: MutableList<Lista>, private val listener: FragmentActivity?) : RecyclerView.Adapter<ForecastAdapter.MyViewHolder>() {
    lateinit var  mContext :Context

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View
        itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)

        return MyViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
          val textViewTimeOfDay =  holder.itemView.findViewById<TextView>(R.id.textViewTimeOfDay)
          val textViewDayOfWeek =  holder.itemView.findViewById<TextView>(R.id.textViewDayOfWeek)
          val imageViewForecastIcon =  holder.itemView.findViewById<ImageView>(R.id.imageViewForecastIcon)
          val textViewTemp =  holder.itemView.findViewById<TextView>(R.id.textViewTemp)
          val textViewTempMax =  holder.itemView.findViewById<TextView>(R.id.tempMax)
          val textViewTempMin =  holder.itemView.findViewById<TextView>(R.id.tempMin)
          val textViewWeatherMain =  holder.itemView.findViewById<TextView>(R.id.textViewWeatherMain)
          val textViewHumidity =  holder.itemView.findViewById<TextView>(R.id.textViewHumidity)
          val textViewHumiditylbl =  holder.itemView.findViewById<TextView>(R.id.textViewHumiditylbl)
          val textViewWeather =  holder.itemView.findViewById<TextView>(R.id.textViewWeather)
          val textViewWind =  holder.itemView.findViewById<TextView>(R.id.textViewWind)

          textViewTimeOfDay.setText(items[position].dt_txt)
          textViewDayOfWeek.setText(getDateTime(items[position].dt.toLong()).toString())
          textViewDayOfWeek.setTextColor(getColor(items[position].dt))
          textViewTemp.setText(getKelVintoCelcius(items[position].main.temp))
          textViewTempMax.setText("Max Temp:".plus(getKelVintoCelcius(items[position].main.temp_max)))
          textViewTempMin.setText("Min Temp:".plus(getKelVintoCelcius(items[position].main.temp_min)))
          loadImage(items[position].weather[0].icon, imageViewForecastIcon)
          textViewWeatherMain.setText(items[position].weather[0].main)
          textViewHumidity.setText(items[position].main.humidity.toString().plus("°"))
          textViewHumiditylbl.setText("Humidity")
          textViewWeather.setText("Weather")
          textViewWind.setText("Wind Speed :".plus(items[position].wind.speed))



    }

    override fun getItemCount(): Int {
       return items.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateTime(s: Long): DayOfWeek? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s * 1000)
            val formattedDate = sdf.format(netDate)

            LocalDate.of(
                formattedDate.substringAfterLast("/").toInt(),
                formattedDate.substringAfter("/").take(2).toInt(),
                formattedDate.substringBefore("/").toInt()
            )
                .dayOfWeek
        } catch (e: Exception) {
            e.printStackTrace()
            DayOfWeek.MONDAY
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getColor(dt: Int): Int {
        return when (dt?.let { getDateTime(it.toLong()) }) {
            DayOfWeek.MONDAY -> Color.parseColor("#28E0AE")
            DayOfWeek.TUESDAY -> Color.parseColor("#FF0090")
            DayOfWeek.WEDNESDAY -> Color.parseColor("#FFAE00")
            DayOfWeek.THURSDAY -> Color.parseColor("#0090FF")
            DayOfWeek.FRIDAY -> Color.parseColor("#DC0000")
            DayOfWeek.SATURDAY -> Color.parseColor("#0051FF")
            DayOfWeek.SUNDAY -> Color.parseColor("#3D28E0")
            else -> Color.parseColor("#28E0AE")
        }
    }

    fun loadImage(icon: String, imageViewForecastIcon: ImageView){

        var imageViewWeatherIcon = imageViewForecastIcon
        when(icon){
            "01d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a01d_svg))
            "01n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a01n_svg))
            "02d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a02d_svg))
            "02n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a02d_svg))
            "03d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a03d_svg))
            "03n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a03n_svg))
            "04d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a04d_svg))
            "04n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a04n_svg))
            "09d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a09d_svg))
            "09n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a09n_svg))
            "10d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a10d_svg))
            "10n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a10n_svg))
            "11d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a11d_svg))
            "11n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a11n_svg))
            "13d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a13d_svg))
            "13n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a13n_svg))
            "50d" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a50d_svg))
            "50n" -> imageViewWeatherIcon.setImageDrawable(listener?.resources?.getDrawable(R.drawable.a50n_svg))

        }
    }

    /*fun getHourColor(): Int {
        return when (dtTxt?.substringAfter(" ")?.substringBeforeLast(":")) {
            "00:00" -> Color.parseColor("#28E0AE")
            "03:00" -> Color.parseColor("#FF0090")
            "06:00" -> Color.parseColor("#FFAE00")
            "09:00" -> Color.parseColor("#0090FF")
            "12:00" -> Color.parseColor("#DC0000")
            "15:00" -> Color.parseColor("#0051FF")
            "18:00" -> Color.parseColor("#3D28E0")
            "21:00" -> Color.parseColor("#50E3FE")
            else -> Color.parseColor("#28E0AE")
        }
    }

    fun getHourOfDay(): String {
        return dtTxt?.substringAfter(" ")?.substringBeforeLast(":") ?: "00:00"
    }*/
}
