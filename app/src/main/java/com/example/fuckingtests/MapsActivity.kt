package com.example.fuckingtests

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Toast
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var map: GoogleMap

    lateinit var currentLocation: Location

    lateinit var locationManager: LocationManager

    private var locationShowed = false

    private lateinit var polyline: Polyline

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            currentLocation = location

            showLocation()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        locationManager = getSystemService(Application.LOCATION_SERVICE) as LocationManager

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_REQUEST_CODE
            )
        } else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
            } else {
                Toast.makeText(this, "Требуется доступ к геолокации", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun showLocation() {
        if (!this::currentLocation.isInitialized || !this::map.isInitialized || locationShowed) return

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(currentLocation.latitude, currentLocation.longitude),
                DEFAULT_ZOOM
        ))

        map.isMyLocationEnabled = true

        locationShowed = true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnMapClickListener(this)

        showLocation()
    }

    companion object {
        const val DEFAULT_ZOOM = 10f

        const val LOCATION_REQUEST_CODE = 100
    }

    override fun onMapClick(clickLatLng: LatLng) {
        if (!this::currentLocation.isInitialized) return

        if (this::polyline.isInitialized)
            polyline.remove()

        polyline = map.addPolyline(PolylineOptions()
                .add(
                        LatLng(currentLocation.latitude, currentLocation.longitude),
                        clickLatLng
                ))

        Toast.makeText(this, "${clickLatLng.latitude} ${clickLatLng.longitude}", Toast.LENGTH_SHORT).show()
    }
}