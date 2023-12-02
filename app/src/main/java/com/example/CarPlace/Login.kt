package com.example.CarPlace

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.content.Intent
import com.example.CarPlace.databinding.ActivityLoginBinding
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page login_main.xml
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    //  Avant d'afficher la page
    override fun onStart() {
        super.onStart()

        // Connexion à l'authent de firebase
        auth = Firebase.auth
        //  On recupere le user courant (comme qd on verifier que la variable session existe)
        val currentUser = auth.currentUser

        //  Si le user connecté alors le ramener vers la page home
        if (currentUser != null) {
            val intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Initialisez le binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progression = binding.progressBar
        val inputEmail = binding.inputEmail
        val inputMdp = binding.inputMdp
        val btnLogin = binding.btnSignIn
        val registerNow = binding.registerNow


        btnLogin.setOnClickListener {

            progression.visibility = View.VISIBLE
            val email = inputEmail.text.toString()
            val password = inputMdp.text.toString()

            //  Cherche via les infos du user si il existe bien dans firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progression.visibility = View.GONE

                    //  Si il existe alors le ramener vers la page home
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
                        Log.w("ECHEC", "Echec Connexion FireBase: echec", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Mail ou mot de passe incorrect",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

        //  Aller sur la page inscription
        registerNow.setOnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
        }


    }


}