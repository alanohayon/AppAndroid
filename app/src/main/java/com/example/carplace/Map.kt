package com.example.carplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.carplace.databinding.ActivityMapBinding
import com.example.carplace.databinding.ActivityUserAccountBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlin.collections.Map
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


// app/src/main/java/com/example/CarPlace/Map.kt
class Map : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap

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
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        // Demande de chargement asynchrone de la carte. Lorsque la carte est prête, onMapReady sera appelé.
        mapFragment.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        // Cette fonction est appelée lorsque la carte est prête à être utilisée.
        Log.d("MapActivity", "La fonction onMapReady est bien appelée")

        if (googleMap == null) {
            Log.e("HHHH MapActivity", "Erreur : La carte est null")
        } else {
            map = googleMap
            val paris = LatLng(48.8566, 2.3522)
            map.addMarker(MarkerOptions().position(paris).title("Marker in Paris"))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 10f))
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        if (mapFragment == null) {
            Log.e("HHHH MapActivity", "Erreur : Le fragment de la carte est null")
        } else {
            Log.d("HHHH MapActivity", "Le fragment de la carte est bien initialisé")
        }

        // LES LOGS
        // MapActivity             com.example.carplace                 D  La fonction onMapReady est bien appelée
        // HHHH MapActivity        com.example.carplace                 D  Le fragment de la carte est bien initialisé

        //todo: problème : tous les logs sont bons mais aucune carte ne s'affiche

        }

}
