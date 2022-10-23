package com.test.weatherapp.utils.location

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat


class LocationProvider(private val context: Context) : Service(), LocationListener {
    lateinit var mLocation: Location
    fun getLocation(): Pair<Double, Double> {
        var latitude: Double = 0.00
        var longitude: Double = 0.00
        try {

            val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager

            // getting GPS status
            val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            // getting network status
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
//                this.canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(
                            (context as Activity?)!!, arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ), 101
                        )
                    }
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, 1000L, 100f, this
                    )
                    mLocation =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                    latitude = mLocation.latitude
                    longitude = mLocation.longitude
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (mLocation == null) {
                        //check the network permission
                        if (ActivityCompat.checkSelfPermission(
                                context, Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                context, Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            ActivityCompat.requestPermissions(
                                (context as Activity?)!!, arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ), 101
                            )
                        }
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 1000L, 100f, this
                        )
                        Log.d("GPS Enabled", "GPS Enabled")
                        mLocation =
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
                        latitude = mLocation.latitude
                        longitude = mLocation.longitude
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(latitude, longitude)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onLocationChanged(p0: Location) {
    }
}