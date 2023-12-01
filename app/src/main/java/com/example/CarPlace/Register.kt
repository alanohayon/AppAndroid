package com.example.CarPlace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.CarPlace.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class Register : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityRegisterBinding

    //  Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth

    //  Avant meme d'afficher la page
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

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progression = binding.progressBar
        val inputNom = binding.inputNom
        val inputPren = binding.inputPren
        val inputEmail = binding.inputEmailUp
        val inputMdp = binding.inputMdpUp
        val inputMdpConf = binding.inputConfMdp
        val inputDate = binding.inputDate
        val btnRegister = binding.btnSignUp
        val loginNow = binding.registerNow

        //  Click sur le lien login
        loginNow.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        btnRegister.setOnClickListener {

            progression.visibility = View.VISIBLE
            val nom= inputNom.text.toString()
            val pren = inputPren.text.toString()
            val email = inputEmail.text.toString()
            val mdp = inputMdp.text.toString()
            val mdpConf = inputMdpConf.text.toString()
            val date = inputDate.text.toString()

            if (email.isEmpty() || mdp.isEmpty() || mdpConf.isEmpty()) {
                Toast.makeText(baseContext, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //  Ajout d'un nouveau user si il n'éxiste pas dans firebase
            auth.createUserWithEmailAndPassword(email, mdp)
                .addOnCompleteListener() { task ->
                    progression.visibility = View.GONE
                    //  success, connexion reussie afficher log/message
                    if (task.isSuccessful) {
                        Log.d("SUCCESS", "createUserWithEmail:success")

                        Toast.makeText(
                            this@Register,
                            "Compte crée 👌.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        Login::class.java
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        //  echec, afficher log/message
                        Log.w("ECHEC", "createUserWithEmail:failure", task.exception)

                        Toast.makeText(
                            this@Register,
                            "Echec de la creation 😕.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }

        }

        //  Aller sur la page connexion
        loginNow.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

    }
}