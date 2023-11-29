package com.example.carplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import com.example.carplace.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    //avant meme d'afficher la page
    override fun onStart() {
        super.onStart()

        auth = Firebase.auth

        //on recupere le user qui courrent (comme qd on verifier que la variable session existe)
        val currentUser = auth.currentUser

        //si le user connecté alors le ramener vers la page home
        if(currentUser != null){
            Toast.makeText(baseContext, "You are already connected.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(baseContext, "You are not connected.", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //variable du btn signIn
        val btnGoSignIn = findViewById(R.id.btnGoSignIn) as Button
        val btnGoSignUp = findViewById(R.id.btnGoSignUp) as Button

        //si il click sur connexion
        btnGoSignIn.setOnClickListener {
            //redirection à la page connexion
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
            Log.d("click login", "click sur btn connexion")
        }

        //si il click sur inscription
        btnGoSignUp.setOnClickListener {
            //redirection à la page inscription
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
            Log.d("click register", "click sur btn inscription")
        }
    }

}