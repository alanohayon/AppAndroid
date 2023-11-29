package com.example.carplace

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.carplace.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    //avant meme d'afficher la page
    override fun onStart() {
        super.onStart()

        auth = Firebase.auth
        val currentUser = auth.currentUser

        //si le user connecté alors le ramener vers la page home
        if (currentUser != null) {
            val intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val progression = findViewById(R.id.progressBar) as ProgressBar
        val inputEmail = findViewById(R.id.inputEmail) as EditText
        val inputMdp = findViewById(R.id.inputMdp) as EditText
        val btnLogin = findViewById(R.id.btnSignIn) as Button
        val registerNow = findViewById(R.id.registerNow) as TextView


        btnLogin.setOnClickListener {

            progression.visibility = View.VISIBLE
            val email = inputEmail.text.toString()
            val password = inputMdp.text.toString()

            //cherche via les info du user si il existe bien dans firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progression.visibility = View.GONE

                    //si il existe alors le ramener vers la page home
                    if (task.isSuccessful) {
                        Log.d("SUCCES", "Connexion:success")
                        Toast.makeText(
                            baseContext,
                            "Authentification reussi.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        val intent = Intent(applicationContext, Home::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // Si il n'existe aps alors lui envoyé une notif
                        Log.w("ECHEC", "Connexion:echec", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Echec de l'authentificaiton.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }


        }

        //aller sur la page inscription
        registerNow.setOnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
        }


    }


}