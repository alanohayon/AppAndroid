package com.example.carplace.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carplace.activity.databinding.ActivityUserAccountBinding
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_map
        Utils.setupMenu(bottomNavView)

        auth = Firebase.auth
        val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
        database = Firebase.database(databaseUrl).reference

        val currentUser = auth.currentUser
        if (currentUser == null) {
            redirectToLogin()
            return
        }

        val userId = currentUser.uid
        loadData(userId)

        binding.btnSave.setOnClickListener {
            saveData(userId)
        }

        binding.logout.setOnClickListener {
            auth.signOut()
            redirectToLogin()
        }

    }

    private fun loadData(userId: String) {
        // Récupération de l'e-mail depuis l'authentification Firebase
        val email = FirebaseAuth.getInstance().currentUser?.email
        binding.emailEditText.setText(email)

        // Récupération des autres informations de l'utilisateur depuis la base de données
        database.child("users").child(userId).get().addOnSuccessListener { snapshot ->
            binding.firstNameEditText.setText(snapshot.child("firstName").getValue(String::class.java))
            binding.lastNameEditText.setText(snapshot.child("lastName").getValue(String::class.java))
            binding.phoneEditText.setText(snapshot.child("phone").getValue(String::class.java))
            binding.cityEditText.setText(snapshot.child("city").getValue(String::class.java))

        }.addOnFailureListener {
            Toast.makeText(this, "Erreur de chargement des données", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveData(userId: String) {
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()
        val city = binding.cityEditText.text.toString()

        val userUpdates = hashMapOf<String, Any>(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phone" to phone,
            "city" to city
        )

        database.child("users").child(userId).updateChildren(userUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Informations enregistrées", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun redirectToLogin() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}