package com.example.CarPlace

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


class Register : AppCompatActivity() {

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
        setContentView(R.layout.activity_register)

        val progression = findViewById(R.id.progressBar) as ProgressBar
        val inputNom = findViewById(R.id.inputNom) as EditText
        val inputPren = findViewById(R.id.inputPren) as EditText
        val inputEmail = findViewById(R.id.inputEmailUp) as EditText
        val inputMdp = findViewById(R.id.inputMdpUp) as EditText
        val inputMdpConf = findViewById(R.id.inputConfMdp) as EditText
        val inputDate = findViewById(R.id.inputDate) as EditText
        val btnRegister = findViewById(R.id.btnSignUp) as Button
        val loginNow = findViewById(R.id.registerNow) as TextView


        //click sur le lien login
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

            //ajout d'un nouveau user si il n'existe pas dans firebase
            auth.createUserWithEmailAndPassword(email, mdp)
                .addOnCompleteListener() { task ->
                    progression.visibility = View.GONE
                    //success, connexion reussie afficher log/message
                    if (task.isSuccessful) {
                        Log.d("SUCCESS", "createUserWithEmail:success")

                        Toast.makeText(
                            Register@this,
                            "Compte crée 👌.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        //echec, afficher log/message
                        Log.w("ECHEC", "createUserWithEmail:failure", task.exception)

                        Toast.makeText(
                            Register@this,
                            "Echec de la creation 😕.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }

        }

        //aller sur la page connexion
        loginNow.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

    }
}