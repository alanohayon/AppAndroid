package com.example.carplace.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager

import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.carplace.activity.databinding.ActivityMapBinding
import com.example.carplace.activity.databinding.InfoMarkerBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.common.internal.Objects.ToStringHelper
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.maps.android.PolyUtil
import com.google.maps.model.TravelMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.LocalDateTime
import java.util.Locale


// app/src/main/java/com/example/CarPlace/Map.kt
class MapActivity : AppCompatActivity(), OnMapReadyCallback{

    private lateinit var binding: ActivityMapBinding
    private lateinit var map: GoogleMap
    private var mGooglemap: GoogleMap? = null
    private lateinit var autocompleteFragment: AutocompleteSupportFragment
    private lateinit var database: DatabaseReference
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var markerUser: Marker? = null
        val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
        database = Firebase.database(databaseUrl).reference
        //recuperer une places
        getMarkerBdd()

//        val latLngOrigin = LatLng(48.7343, 2.5433) // Ayala
//        val latLngDestination = LatLng(48.7393,2.5493) // SM City

        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_map
        Utils.setupMenu(bottomNavView)

        // Affichage de la carte (async) avec les markers
        val mapFragment = supportFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mGooglemap = googleMap
            val infoMarkerBinding = InfoMarkerBinding.inflate(layoutInflater)
            val rootView = findViewById<ViewGroup>(android.R.id.content)

//            mGooglemap?.addMarker(MarkerOptions().position(latLngOrigin).title("Ayala"))
//            mGooglemap?.addMarker(MarkerOptions().position(latLngDestination).title("SM City"))
//            mGooglemap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngOrigin, 14.5f))

//            val apiKey = getString(R.string.mapApiKey)
//            drawRoute(mGooglemap!!, latLngOrigin, latLngDestination, apiKey)


            // Click sur un marker
            mGooglemap?.setOnMarkerClickListener {clickedMarker ->

                rootView.addView(infoMarkerBinding.root)

                infoMarkerBinding.adress.text = clickedMarker.snippet

                // Si il click sur le bouton "reserver"
                infoMarkerBinding.takePlace.setOnClickListener {
                    // renvoyer vers la page ParkActivity
                    val id = clickedMarker.title
                    // recuper la date et l'heure actuel
                    val currentDateTime = LocalDateTime.now()
//                    Toast.makeText(this@MapActivity, "Vous avez reserver une place", Toast.LENGTH_SHORT).show()
                    if (id != null) {
                        database.child("places").child(id).child("userId").setValue(userId)
                        database.child("places").child(id).child("status").setValue("used")
                        database.child("places").child(id).child("date").setValue(currentDateTime.toString())
                        if (userId != null) {
                            database.child("users").child(userId).child("place").setValue(id)
                        }


                    }

                    val intent = Intent(this, ParkActivity::class.java)
                    startActivity(intent)

                    // Gestionnaire pour le bouton "S'y rendre"
                }

                true
            }

            mGooglemap?.setOnMapClickListener {
                // Supprimez la vue infoMarkerBinding de la vue racine
                rootView.removeView(infoMarkerBinding.root)
            }
        }

        binding.btnAddPlace.setOnClickListener {
           // recuperer laddrss

            getUserLocation { userLocation ->
                val userLoca = LatLng(userLocation.latitude, userLocation.longitude)
                getAddressFromLatLng(userLoca) { address ->

                    // ajouter la place à la bdd
                    val newPlaceRef = database.push()
                    newPlaceRef.child("places").child("lat").setValue(userLocation.latitude)
                    newPlaceRef.child("places").child("lng").setValue(userLocation.longitude)
                    newPlaceRef.child("places").child("name").setValue(address)
                    newPlaceRef.child("places").child("status").setValue("free")
                    newPlaceRef.child("places").child("date").setValue("")



                    Toast.makeText(this, "Place ajoutée", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MapActivity::class.java)
                    startActivity(intent)
                }
            }
        }


        val iconPosition = BitmapDescriptorFactory.fromResource(R.drawable.icon_position)

        val btnOptionMap = binding.mapOptionBtn
        val optionMap = PopupMenu(this, btnOptionMap)
        optionMap.menuInflater.inflate(R.menu.map_options, optionMap.menu)

        // Changer le type de la carte
        btnOptionMap.setOnClickListener {
            optionMap.show()
        }
        // Affichage des items du menu
        optionMap.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }

        // Recuperer sa localisation
        getUserLocation { userLocation ->
            val userLoca = LatLng(userLocation.latitude, userLocation.longitude)
            getAddressFromLatLng(userLoca) { address ->
                markerUser = mGooglemap?.addMarker(
                    MarkerOptions().position(userLocation).icon(iconPosition).title("Vous êtes ici")
                        .snippet(address)
                )
                zoomOnMap(userLocation, iconPosition)
            }

        }


        // LA RECHERCHE
        Places.initialize(applicationContext, getString(R.string.mapApiKey))
        autocompleteFragment = supportFragmentManager.findFragmentById(R.id.inputSearch)
                as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        //  Lorsqu'il fait une recherche
        autocompleteFragment.setOnPlaceSelectedListener(object : com.google.android.libraries.places.widget.listener.PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i("Map", "Place: ${place.name}, ${place.id}")
                val location = place.latLng
//                val marker = mGooglemap?.addMarker(MarkerOptions().position(location!!).icon(iconPosition).title(place.name).snippet(place.address))
                mGooglemap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
            }
            override fun onError(p0: Status) {
                Log.i("Map", "Erreur lors de la recherche: $p0")
                Toast.makeText(this@MapActivity, "Erreur lors de la recherche: $p0", Toast.LENGTH_SHORT).show()
            }

        })

//        OnInfoWindowClickListener { marker ->
//            val intent = Intent(this, ParkActivity::class.java)
//            val newPlaceRef = database.child("places").push()
//            newPlaceRef.child("lat").setValue(48.8471)
//            newPlaceRef.child("lng").setValue(2.2891)
//
//            startActivity(intent)
//        }

        // Lorsqu'il clic pour revenir a sa position
        binding.btnMyLocation.setOnClickListener {
            markerUser?.remove()
            getUserLocation { userLocation ->
                val userLoca = LatLng(userLocation.latitude, userLocation.longitude)
                getAddressFromLatLng(userLoca) { address ->
                    markerUser = mGooglemap?.addMarker(
                        MarkerOptions().position(userLocation).icon(iconPosition).title("Vous êtes ici")
                            .snippet(address)
                    )
                    zoomOnMap(userLocation, iconPosition)
                }

            }
            // Gestionnaire pour le bouton "S'y rendre"
        }

    }


    // Zoom sur la carte
    private fun zoomOnMap(location: LatLng, iconPosition: BitmapDescriptor) {

        mGooglemap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))


    }

    fun drawRoute(map: GoogleMap, origin: LatLng, destination: LatLng, apiKey: String) {
        val context = GeoApiContext.Builder()
            .apiKey(apiKey)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val results = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING) // ou autre mode de transport
                    .origin(com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                    .destination(com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                    .await()

                withContext(Dispatchers.Main) {
                    if (results.routes.isNotEmpty()) {
                        val route = results.routes.first()
                        val decodedPath = PolyUtil.decode(route.overviewPolyline.encodedPath)

                        map.addPolyline(PolylineOptions().addAll(decodedPath))
                    }
                }
            } catch (e: ApiException) {
                Log.e("MapsAPI", "ApiException: ${e.message}")
            } catch (e: InterruptedException) {
                Log.e("MapsAPI", "InterruptedException: ${e.message}")
            } catch (e: IOException) {
                Log.e("MapsAPI", "IOException: ${e.message}")
            } catch (e: Exception) {
                Log.e("MapsAPI", "Exception: ${e.message}")
            }
        }
    }

    private fun getMarkerBdd(){
        database.child("places").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (placeSnapshot in dataSnapshot.children) {
                        val lat = placeSnapshot.child("lat").getValue(Double::class.java)
                        val lng = placeSnapshot.child("lng").getValue(Double::class.java)
                        val status = placeSnapshot.child("status").getValue(String::class.java)
                        val uId = placeSnapshot.child("userId").getValue(String::class.java)
                        val id = placeSnapshot.key
                        if (status != null) {
                            if (status == "used" && uId == userId){
                                Toast.makeText(this@MapActivity, "Vous avez une place", Toast.LENGTH_LONG).show()
                                if (lat != null && lng != null) {
                                    val loca = LatLng(lat, lng)
                                    getAddressFromLatLng(loca) { address ->

                                        mGooglemap?.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(id).snippet(address).icon(BitmapDescriptorFactory.fromResource(R.drawable.place_user)))

                                    }
                                }
                            }
                            if (status != "used"){
                                if (lat != null && lng != null) {
                                    val loca = LatLng(lat, lng)
                                    getAddressFromLatLng(loca) { address ->

                                        mGooglemap?.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(id).snippet(address))

                                    }
                                }
                            }

                        }

                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Gérez les erreurs ici
                Toast.makeText(applicationContext, "Erreur: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserLocation(callback: (LatLng) -> Unit) {
        checkLocationSettings()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Gérer le cas où les permissions ne sont pas accordées
            return
        }

        // Après la verification de la permission
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token
            override fun isCancellationRequested() = false
        }).addOnSuccessListener { location: Location? ->
            if (location == null) {
                // Gérer le cas où la localisation est null
                Toast.makeText(this, "Erreur de la localisation.", Toast.LENGTH_SHORT).show()
            } else {
                val userLocation = LatLng(location.latitude, location.longitude)
                callback(userLocation)
            }
        }
    }

    companion object {
        private const val REQUEST_CHECK_SETTINGS = 1001
    }
    private fun checkLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient: SettingsClient = LocationServices.getSettingsClient(this)
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnSuccessListener {
            // Les paramètres de localisation sont satisfaisants, et on peut initialiser les mises à jour de localisation ici
            return@addOnSuccessListener
        }

        locationSettingsResponseTask.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // La localisation n'est pas activée, mais on peut demander à l'utilisateur de l'activer

                try {
                    exception.startResolutionForResult(this@MapActivity, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Toast.makeText(this@MapActivity, "Veuillez activer votre localisation", Toast.LENGTH_SHORT).show()
                    // Ignorer l'erreur ou la traiter ici
                }
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


    private fun getAddressFromLatLng(location: LatLng, callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val geocoder = Geocoder(this@MapActivity, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = if (addresses?.isNotEmpty() == true) {
                    addresses[0].getAddressLine(0)
                } else {
                    "Adresse introuvable"
                }
                withContext(Dispatchers.Main) {
                    callback(address)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback("Erreur lors de la récupération de l'adresse")
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGooglemap = googleMap

        }



}

