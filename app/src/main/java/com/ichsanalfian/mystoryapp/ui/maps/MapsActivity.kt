package com.ichsanalfian.mystoryapp.ui.maps

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ichsanalfian.mystoryapp.R
import com.ichsanalfian.mystoryapp.databinding.ActivityMapsBinding
import com.ichsanalfian.mystoryapp.utils.ViewModelFactory


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by viewModels { factory }
    private lateinit var factory: ViewModelFactory
    private var storyPlace: List<StoryPlace> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = ViewModelFactory.getInstance(this)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        getMyLocation()
        addManyMarker()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    data class StoryPlace(
        val name: String,
        val latitude: Any,
        val longitude: Any
    )

    private fun addManyMarker() {
        mapsViewModel.getUser().observe(this) { data ->
            if (data.isLogin) {
                mapsViewModel.getLocation(data.token)
            }
        }
        mapsViewModel.story.observe(this) { data ->
            storyPlace = data.listStory.map { story ->
                StoryPlace(story.name, story.lat, story.lon)
            }
            storyPlace.forEach { story ->
                val latLng = LatLng(story.latitude as Double, story.longitude as Double)
                mMap.addMarker(MarkerOptions().position(latLng).title(story.name))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

            }
        }
    }
}