package com.example.carplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.carplace.databinding.ActivityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import kotlin.collections.Map
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


// app/src/main/java/com/example/CarPlace/Map.kt
class Map : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private var mGooglemap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
        // Recherche du fragment de la carte dans le layout XML par son ID et le cast en SupportMapFragment.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment

        val mapFragment = supportFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        // Demande de chargement asynchrone de la carte. Lorsque la carte est prête, onMapReady sera appelé.
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
