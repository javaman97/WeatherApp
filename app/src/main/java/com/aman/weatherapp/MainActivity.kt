package com.aman.weatherapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aman.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_LOW_POWER
import okhttp3.internal.http2.Settings
import java.util.Locale

const val APP_ID: String = BuildConfig.API_KEY
const val BASE_URL: String = BuildConfig.BASE_URL

class MainActivity : AppCompatActivity() {
    // To get city name from location coordinates
    private lateinit var geocoder: Geocoder
    //  To get location coordinates of user
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.getDefault())
        getCurrentLocationWeather()
        searchCity()


    }

    private fun checkPermission(): Boolean {
        val fineLocation = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        )== PackageManager.PERMISSION_GRANTED

        val coarseLocation = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if(fineLocation && coarseLocation) return true
        return false
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager:LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(
            LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getCurrentLocationWeather() {
        if(checkPermission()){
            if(isLocationEnabled()){
                // Find Latitude & Longitute
                fusedLocationProviderClient.getCurrentLocation(PRIORITY_LOW_POWER, null)
                    .addOnSuccessListener {
                        if(it!=null){
                            val latitude = it.latitude
                            val longitude = it.longitude

                            getCityFromCoordinates(latitude,longitude){ currentCity ->
                                fetchWeatherData(currentCity!!)
                            }
                        }else{
                            Toast.makeText(applicationContext,
                                "System Not Responding",
                                Toast.LENGTH_LONG).show()
                        }
                    }
            } else{
                //Open Settings to Enable Location
                Toast.makeText(applicationContext, "Turn on Location", Toast.LENGTH_LONG).show()
                val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            requestPermission()
        }
    }

    private fun getCityFromCoordinates(latitude: Double, longitude: Double, callback: (String) -> Unit) {
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                
            }
        }

    }

    private fun searchCity() {

    }
}