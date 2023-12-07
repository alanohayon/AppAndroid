package com.example.carplace

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.carplace.databinding.ActivityMapBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment


// app/src/main/java/com/example/CarPlace/Map.kt
class Map : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private var mGooglemap: GoogleMap? = null
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val iconPosition = BitmapDescriptorFactory.fromResource(R.drawable.icon_position)


        // LA NAVIGATION
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_park
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> { startActivity(Intent(this, Home::class.java)); true }
                R.id.nav_park -> { true }
                R.id.nav_car -> { startActivity(Intent(this, CarInfo::class.java)); true }
                R.id.nav_account -> { startActivity(Intent(this, UserAccount::class.java)); true }
                else -> false
            }
        }

        // LA CARTE
        val mapFragment = supportFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        // Lorsque la carte est prête, onMapReady sera appelé.
        mapFragment.getMapAsync(this)


        val btnOptionMap = binding.mapOptionBtn
        val optionMap = PopupMenu(this, btnOptionMap)
        optionMap.menuInflater.inflate(R.menu.map_options, optionMap.menu)

        optionMap.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }

        btnOptionMap.setOnClickListener {
            optionMap.show()
        }

        // LA RECHERCHE
        Places.initialize(applicationContext, getString(R.string.mapApiKey))
        autocompleteFragment = supportFragmentManager.findFragmentById(R.id.inputSearch)
                as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Check la permission de localisation
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        //  Lorsqu'il fait une recherche
        autocompleteFragment.setOnPlaceSelectedListener(object : com.google.android.libraries.places.widget.listener.PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i("Map", "Place: ${place.name}, ${place.id}")
                val location = place.latLng
                zoomOnMap(location!!, iconPosition)
            }

            override fun onError(p0: Status) {
                Log.i("Map", "An error occurred: $p0")
                Toast.makeText(this@Map, "An error occurred: $p0", Toast.LENGTH_SHORT).show()
            }

        })

        //  Si la position actuelle est disponible, zoom sur la carte
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                //   Zoom sur la carte avec la position actuelle
                if (location != null) {
                    //  ECE par defaut
                    val location = LatLng(48.85209, 2.28602)
                    zoomOnMap(location, iconPosition)
                } else {
                    //  La derniere  position connue n'est pas disponible
                    Toast.makeText(this@Map, "Last known location not available", Toast.LENGTH_SHORT).show()
                }
            }
            //   Si il y a une erreur
            .addOnFailureListener { e ->
                Log.e("Map", "Error getting last known location: $e")
                Toast.makeText(this@Map, "Error getting last known location", Toast.LENGTH_SHORT).show()
            }


    }

    private fun zoomOnMap(location: LatLng, iconPosition: BitmapDescriptor) {

        mGooglemap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
        val marker = mGooglemap?.addMarker(MarkerOptions().position(location).icon(iconPosition))

        mGooglemap?.setOnMarkerClickListener { clickedMarker ->
            if (clickedMarker == marker) {
                //afficher un toats
                Toast.makeText(this@Map, "Marker clicked", Toast.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }
    }



    private fun changeMap(itemId: Int) {
        when (itemId) {
            R.id.normal_map -> mGooglemap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.satellite_map -> mGooglemap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.hybrid_map -> mGooglemap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.terrain_map -> mGooglemap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
            else -> mGooglemap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGooglemap = googleMap

        }

}

