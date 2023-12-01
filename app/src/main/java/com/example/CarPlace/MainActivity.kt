package com.example.CarPlace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.content.Intent
import com.example.CarPlace.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class MainActivity : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityMainBinding

    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth

    //  Connexion à databse realtime
    private val database = Firebase.database

    //  Connexion ou creation à la table
    private val myRef = database.getReference("BDD-CarPlace")


    //  Avant meme d'afficher la page
    override fun onStart() {
        super.onStart()

        myRef.ref.child("Car")
        auth = Firebase.auth

        //  On recupere le user courrent (comme qd on verifier que la variable session existe)
        val currentUser = auth.currentUser

        //si le user connecté alors le ramener vers la page home
        if(currentUser != null){
            Toast.makeText(baseContext, "You are already connected.", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(baseContext, "You are not connected.", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Initialisez le binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Variable des btn
        val btnGoSignIn = binding.btnGoSignIn
        val btnGoSignUp = binding.btnGoSignUp

        //  Click sur connexion
        btnGoSignIn.setOnClickListener {
            //redirection à la page connexion
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
            Log.d("click login", "click sur btn connexion")
        }

        //  Click sur inscription
        btnGoSignUp.setOnClickListener {
            //redirection à la page inscription
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
            Log.d("click register", "click sur btn inscription")
        }
    }

}
