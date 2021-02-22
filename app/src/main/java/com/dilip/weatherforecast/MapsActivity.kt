package com.dilip.weatherforecast

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dilip.weatherforecast.data.local.dao.SQLHelper
import com.dilip.weatherforecast.extension.getFormatTime
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*


internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var databaseHandler: SQLHelper? = null
    private var db :SQLiteDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         databaseHandler = SQLHelper(this)
         db = databaseHandler!!.getWritableDatabase()
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        val address = LatLng(18.589800, 73.743202)
        mMap.addMarker(
                MarkerOptions()
                        .position(address)
                        .title(getAddress(address.latitude, address.longitude))
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(address, 15f))

        mMap.setOnMapClickListener {
            mMap.addMarker(
                    MarkerOptions()
                            .position(LatLng(it.latitude, it.longitude))
                            .title(getAddress(it.latitude, it.longitude))

            )
            /**
             * add location in sqlite
             */
            db?.let { it1 -> databaseHandler?.insertLocation(it?.latitude.toString(), it?.longitude.toString(),getAddress(it.latitude, it.longitude), getFormatTime(), it1) }

        }
    }

    fun getAddress(lat: Double, lng: Double) :String{
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add: String = obj.getAddressLine(0)
            add = """
            $add
            ${obj.getCountryName()}
            """.trimIndent()
            add = """
            $add
            ${obj.getCountryCode()}
            """.trimIndent()
            add = """
            $add
            ${obj.getAdminArea()}
            """.trimIndent()
            add = """
            $add
            ${obj.getPostalCode()}
            """.trimIndent()
            add = """
            $add
            ${obj.getSubAdminArea()}
            """.trimIndent()
            add = """
            $add
            ${obj.getLocality()}
            """.trimIndent()
            add = """
            $add
            ${obj.getSubThoroughfare()}
            """.trimIndent()
            Log.e("Final address", "Address$add")
            // Toast.makeText(this, "Address=>" + add,

            return add.trimStart()

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        return ""
    }

}