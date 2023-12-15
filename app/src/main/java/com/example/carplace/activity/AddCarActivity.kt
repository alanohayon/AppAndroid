package com.example.carplace.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.firebase.database.DatabaseReference
import android.widget.Toast
import com.example.carplace.activity.databinding.ActivityAddCarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.carplace.activity.R

class AddCarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCarBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
        database = Firebase.database(databaseUrl).reference

        binding.btnAjouter.setOnClickListener {
            val model = binding.editTextModel.text.toString()
            val marque = binding.editTextMarque.text.toString()
            val type = binding.editTextType.text.toString()

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener

            database.child("users").child(userId).child("car_informations").child("model").setValue(model)
            database.child("users").child(userId).child("car_informations").child("type").setValue(type)
            database.child("users").child(userId).child("car_informations").child("marque").setValue(marque)

            Toast.makeText(this, "Informations du véhicule enregistrées", Toast.LENGTH_SHORT).show()
        }

        binding.btnAnnuler.setOnClickListener {
            finish()
        }
    }
}