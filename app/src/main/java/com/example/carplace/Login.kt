package com.example.carplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.carplace.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityLoginBinding

    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        // Connexion à l'authent de firebase
        auth = Firebase.auth

        //  On recupere le user courant (comme qd on verifier que la variable session existe)
        val currentUser = auth.currentUser

        //  Si le user connecté alors le ramener vers la page home
//        if (currentUser != null) {
//            val intent = Intent(applicationContext, Home::class.java)
//            startActivity(intent)
//            finish()
//        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisez le binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar = binding.progressBar
        val btnSignIn = binding.btnSignIn
        val btnGoSignUp = binding.btnGoSignUp
        val inputEmail = binding.inputEmail
        val inputPwd = binding.inputPwd

        btnSignIn.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            val email = inputEmail.text.toString()
            val password = inputPwd.text.toString()

            //cherche via les info du user si il existe bien dans firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE

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

        //  Aller sur la page inscription
        btnGoSignUp.setOnClickListener {
            val intent = Intent(applicationContext, Register::class.java)
            startActivity(intent)
            finish()
        }
    }
}