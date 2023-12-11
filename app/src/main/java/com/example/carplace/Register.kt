package com.example.carplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.carplace.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Register : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityRegisterBinding

    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth

    // Instance de la BDD
    private lateinit var database: DatabaseReference

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

        val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
        database = Firebase.database(databaseUrl).reference


        // Initialisez le binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnGoSignIn = binding.btnGoSignIn
        val btnSignUp = binding.btnSignUp
        val inputName = binding.inputName
        val inputFirstName = binding.inputFirstName
        val inputEmail = binding.inputEmailUp
        val inputPwd = binding.inputPwd
        val inputPwdConf = binding.inputConfPwd
        val inputDate = binding.inputDate
        val progressBar = binding.progressBar

        //  Click sur CONNEXION
        btnGoSignIn.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        btnSignUp.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            val lastName = inputName.text.toString()
            val firstName = inputFirstName.text.toString()
            val email = inputEmail.text.toString()
            val mdp = inputPwd.text.toString()
            val mdpConf = inputPwdConf.text.toString()
            val birthDate = inputDate.text.toString()

            if (email.isEmpty() || mdp.isEmpty() || mdpConf.isEmpty()) {
                Toast.makeText(baseContext, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            //  Ajout d'un nouveau user si il n'existe pas dans firebase
            auth.createUserWithEmailAndPassword(email, mdp)
                .addOnCompleteListener() { task ->
                    progressBar.visibility = View.GONE
                    //success, connexion reussie afficher log/message
                    if (task.isSuccessful) {
                        Log.d("SUCCESS", "createUserWithEmail:success")

                        // Récupération de l'ID de l'utilisateur créé
                        val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                        // Enregistrement des informations supplémentaires dans la base de données
                        database.child("users").child(userId).child("firstName").setValue(firstName)
                        database.child("users").child(userId).child("email").setValue(email)
                        database.child("users").child(userId).child("lastName").setValue(lastName)
                        database.child("users").child(userId).child("birthDate").setValue(birthDate)


                        Toast.makeText(
                            this@Register,
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
                            this@Register,
                            "Echec de la creation 😕.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }

        }

        //  Aller sur la page connexion
        btnGoSignIn.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }


    }
}