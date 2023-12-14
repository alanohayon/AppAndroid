package com.example.carplace.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.carplace.activity.databinding.ActivityParkBinding
import com.example.carplace.activity.databinding.PlaceUsedBinding

import com.example.carplace.activity.databinding.InfoMarkerBinding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ParkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkBinding
    private lateinit var database: DatabaseReference
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityParkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
        database = Firebase.database(databaseUrl).getReference("places")

        getMarkerByIdUser()

        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_park
        Utils.setupMenu(bottomNavView)
    }
    private fun getMarkerByIdUser() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val inflater = layoutInflater
                if (dataSnapshot.exists()) {
                    for (placeSnapshot in dataSnapshot.children) {
                        val id = placeSnapshot.child("userId").getValue(String::class.java)
                        val status = placeSnapshot.child("status").getValue(String::class.java)
                        if (id == userId && status == "used") {
                            val placeUsedView = inflater.inflate(R.layout.place_used, binding.root, false)

                            val lat = placeSnapshot.child("lat").getValue(Double::class.java)
                            val lng = placeSnapshot.child("lng").getValue(Double::class.java)
                            val name = placeSnapshot.child("name").getValue(String::class.java)
                            val date = placeSnapshot.child("date").getValue(String::class.java)

                            Log.d("SSS", "ID user: $name")

                            val btnLeave = placeUsedView.findViewById<Button>(R.id.btnLeave)
                            val btnGoTo = placeUsedView.findViewById<Button>(R.id.btnGoTo)
                            val textViewPlace = placeUsedView.findViewById<TextView>(R.id.textViewPlaceAdresse)
                            val textViewDate = placeUsedView.findViewById<TextView>(R.id.textViewHeureDebut)
                            textViewPlace.text = name
                            textViewDate.text = date


                            btnLeave.setOnClickListener {
                                database.child(placeSnapshot.key.toString()).child("status").setValue("free")
                                Toast.makeText(this@ParkActivity, "Vous avez quitté votre place", Toast.LENGTH_LONG).show()
                                // Recharger les données ou mettre à jour l'UI ici au lieu de redémarrer l'activité
                                getMarkerByIdUser() // Appel récursif pour rafraîchir les données
                            }

                            binding.root.addView(placeUsedView)

                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Erreur: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}