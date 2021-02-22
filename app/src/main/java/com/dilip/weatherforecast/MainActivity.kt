package com.dilip.weatherforecast

//import com.dilip.weatherforecast.data.local.DatabaseBuilder
//import com.dilip.weatherforecast.data.local.DatabaseHelperImpl
import PreferenceHelper.customPreference
import PreferenceHelper.lan
import PreferenceHelper.lat
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dilip.weatherforecast.adapter.DataAdapter
import com.dilip.weatherforecast.data.api.ApiHelperImpl
import com.dilip.weatherforecast.data.api.RetrofitBuilder
import com.dilip.weatherforecast.data.local.dao.SQLHelper
import com.dilip.weatherforecast.util.CustomListViewDialog
import com.dilip.weatherforecast.util.Status
import com.dilip.weatherforecast.util.ViewModelFactory
import com.dilip.weatherforecast.viewmodels.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    private lateinit var weatherViewModel: WeatherViewModel
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val degree :String = "°"
    val CUSTOM_PREF_NAME = "location_data"

    private val MY_PERMISSIONS_REQUEST_LOCATION = 101
    private var isPermission = false
    private var fusedLocationClient: FusedLocationProviderClient? = null
    internal var customDialog: CustomListViewDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionSetup()
        setUpUI()
        setupViewModel()
        setupObserver()


    }

    private fun getDataFromDb() {
        val helper = SQLHelper(this)
        val db: SQLiteDatabase = helper.getReadableDatabase()

        val cursor = db.rawQuery("select * from location ", arrayOf())
        cursor?.moveToFirst()
        if (cursor.count > 0) {
            do {
                val id = cursor.getInt(0)
                val lat = cursor.getString(1)
                val lan = cursor.getString(2)
                val city = cursor.getString(3)

                Log.e("databse entry:",id.toString()+lat+lan+city)


            }while (cursor.moveToNext())

        }

         var items = arrayListOf(
                "Apple Apple Apple ",
                "Banana",
                "Orange",
                "Grapes",
                "Apple",
                "Banana",
                "Orange",
                "Grapes",
                "Apple",
                "Banana",
                "Orange",
                "Grapes",
                "Apple",
                "Banana",
                "Orange",
                "Grapes",
                "Apple",
                "Banana",
                "Orange",
                "Grapes"
        )


        val dataAdapter = DataAdapter(items, this)
        customDialog = CustomListViewDialog(this, dataAdapter)

        //if we know that the particular variable not null any time ,we can assign !! (not null operator ), then  it won't check for null, if it becomes null, it willthrow exception
        customDialog!!.show()
        customDialog!!.setCanceledOnTouchOutside(false)
    }

    private fun permissionSetup() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkPermissin()
    }

    private fun checkPermissin() {
        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ), MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            isPermission = true

            fusedLocationClient!!.lastLocation
                .addOnSuccessListener(
                        this
                ) { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        val currentLat = location.latitude
                        val currentLang = location.longitude
                        Log.e("latitude permission :$currentLat", " logitude :$currentLang")

                        val prefs = customPreference(this, CUSTOM_PREF_NAME)
                            prefs.lat = currentLat.toString()
                            prefs.lan = currentLang.toString()
                        Log.e("latitude Pref :${prefs.lan}", " logitude :${prefs.lan}")
                    }
                }

        }
    }


    private fun setupObserver() {

        weatherViewModel.getWeather().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    //progressBar.visibility = View.GONE
                    println(it)
                    Log.e("weather response is:", it.toString())


                    val textViewCityName: TextView = findViewById(R.id.cityName)
                    val textViewTemperature: TextView = findViewById(R.id.textViewTemperature)
                    val textViewWeatherMain: TextView = findViewById(R.id.textViewWeatherMain)
                    val textViewHumidity: TextView = findViewById(R.id.textViewHumidity)
                    val textViewMinTemp: TextView = findViewById(R.id.tempMin)
                    val textViewMaxTemp: TextView = findViewById(R.id.tempMax)
                    val textViewLatitude: TextView = findViewById(R.id.latitude)
                    val textViewLongitude: TextView = findViewById(R.id.longitude)

                    textViewCityName.setText(it.data?.name)
                    textViewTemperature.setText(getKelVintoCelcius(it.data!!.main.temp))
                    textViewHumidity.setText(it.data.main.humidity.toString().plus(degree))
                    textViewWeatherMain.setText(
                            it.data.weather[0].main.plus(" ").plus(it.data.weather[0].description)
                    )
                    loadImage(it.data.weather[0].icon)
                    textViewMinTemp.setText(getKelVintoCelcius(it.data.main.temp_min))
                    textViewMaxTemp.setText(getKelVintoCelcius(it.data.main.temp_max))
                    textViewLatitude.setText("Lat: ".plus(it.data.coord.lat.toString()))
                    textViewLongitude.setText("Lon: ".plus(it.data.coord.lon.toString()))


                }
                Status.LOADING -> {
                    //progressBar.visibility = View.VISIBLE
                    //recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    Log.e("eroor is:", it.toString())
                }
            }
        })

    }

    private fun setupViewModel() {
        weatherViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(
                        ApiHelperImpl(RetrofitBuilder.apiService),
                        //DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                )
        ).get(WeatherViewModel::class.java)
    }

    private fun setUpUI() {

        val txtView :TextView = findViewById(R.id.addLocation)
        val viewLocation :TextView = findViewById(R.id.viewLocation)

        txtView.setOnClickListener {
            startActivity(Intent(this@MainActivity, MapsActivity::class.java))
        }

        viewLocation.setOnClickListener {
            //startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            getDataFromDb()

        }

        /**
         * place api intilization
         */
        val apiKey = resources.getResourceName(R.string.google_maps_key)
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))


        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.e("TAG", "Place: ${place.name}, ${place.id}")
            }

            override fun onError(p0: com.google.android.gms.common.api.Status) {
                Log.e("TAG", "An error occurred: $p0")
                /**
                 * due to api of place getting error
                 */
            }

        })

    }

    /**
     * get temp in celcius
     */
    fun getKelVintoCelcius(temprature: Double):String{
        val tempInCel= temprature- 273.15
        return tempInCel.roundToInt().toString()+"°"
    }

    /**
     * load icon
     */

     fun loadImage(icon: String){
        val imageViewWeatherIcon : ImageView = findViewById(R.id.imageViewWeatherIcon)
         when(icon){
             "01d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a01d_svg))
             "01n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a01n_svg))
             "02d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a02d_svg))
             "02n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a02d_svg))
             "03d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a03d_svg))
             "03n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a03n_svg))
             "04d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a04d_svg))
             "04n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a04n_svg))
             "09d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a09d_svg))
             "09n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a09n_svg))
             "10d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a10d_svg))
             "10n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a10n_svg))
             "11d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a11d_svg))
             "11n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a11n_svg))
             "13d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a13d_svg))
             "13n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a13n_svg))
             "50d" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a50d_svg))
             "50n" -> imageViewWeatherIcon.setImageDrawable(resources.getDrawable(R.drawable.a50n_svg))

         }
     }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            isPermission = true
            if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )
                == PackageManager.PERMISSION_GRANTED
            ) fusedLocationClient!!.lastLocation
                .addOnSuccessListener(
                        this
                ) { location ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        val currentLat = location.latitude
                        val currentLang = location.longitude
                        Log.e("LAT: $currentLat", "Long $currentLang")

                        val prefs = customPreference(this, CUSTOM_PREF_NAME)
                        prefs.lat = currentLat.toString()
                        prefs.lan = currentLang.toString()
                        Log.e("latitude Pref first  :${prefs.lan}", " logitude :${prefs.lan}")

                    }
                }
        } else {
            checkPermissin()
        }
    }

}