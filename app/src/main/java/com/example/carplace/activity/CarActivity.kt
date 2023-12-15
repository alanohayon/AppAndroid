package com.example.carplace.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.carplace.activity.databinding.ActivityCarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference

class CarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_map
        Utils.setupMenu(bottomNavView)

        binding.btnAddCarInfos.setOnClickListener {
            val intent = Intent(this, AddCarActivity::class.java)
            startActivity(intent)
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {

            val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
            database = Firebase.database(databaseUrl).reference

            database.child("users").child(userId).child("car_informations").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val model = snapshot.child("model").getValue(String::class.java) ?: "Non spécifié"
                    val marque = snapshot.child("marque").getValue(String::class.java) ?: "Non spécifié"
                    val type = snapshot.child("type").getValue(String::class.java) ?: "Non spécifié"

                    binding.daughterLayout2.findViewById<TextView>(R.id.textModel).text = "Modèle : $model"
                    binding.daughterLayout2.findViewById<TextView>(R.id.textMarque).text = "Marque : $marque"
                    binding.daughterLayout2.findViewById<TextView>(R.id.textType).text = "Type : $type"
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CarActivity, "Erreur de chargement des données", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}