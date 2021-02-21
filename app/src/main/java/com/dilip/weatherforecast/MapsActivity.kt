package com.dilip.weatherforecast

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val address = LatLng(18.589800, 73.743202)
        mMap.addMarker(
            MarkerOptions()
                .position(address)
                .title("Marker in Pune")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(address))

        mMap.setOnMapClickListener {
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(it.latitude, it.longitude))
                    .title(getAddress(it.latitude,it.longitude))
            )


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