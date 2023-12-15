package com.example.carplace.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.carplace.activity.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityRegisterBinding

    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        // Connexion à l'authent de firebase
        auth = Firebase.auth

        //  On recupere le user courant (comme qd on verifier que la variable session existe)
        val currentUser = auth.currentUser

        //  Si le user connecté alors le ramener vers la page home
        if (currentUser != null) {
            val intent = Intent(applicationContext, MapActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_map
        Utils.setupMenu(bottomNavView)

        // Initialisez le binding


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
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
//
//        btnSignUp.setOnClickListener {
//
//            progressBar.visibility = View.VISIBLE
//            val nom = inputName.text.toString()
//            val prenom = inputFirstName.text.toString()
//            val email = inputEmail.text.toString()
//            val mdp = inputPwd.text.toString()
//            val dateNaissance = inputDate.text.toString()
//
//            if (email.isEmpty() || mdp.isEmpty()) {
//                Toast.makeText(baseContext, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show()
//                progressBar.visibility = View.GONE
//                return@setOnClickListener
//            }
//
//            // Ajout d'un nouveau user dans Firebase Authentication
//            auth.createUserWithEmailAndPassword(email, mdp)
//                .addOnCompleteListener(this) { task ->
//                    progressBar.visibility = View.GONE
//                    if (task.isSuccessful) {
//                        // Utilisateur créé avec succès
//                        val userId = auth.currentUser?.uid
//                        if (userId != null) {
//                            val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
//                            val database = Firebase.database(databaseUrl).reference
//
//                            // Enregistrer les informations supplémentaires de l'utilisateur
//                            database.child("users").child(userId).child("nom").setValue(nom)
//                            database.child("users").child(userId).child("prenom").setValue(prenom)
//                            database.child("users").child(userId).child("dateNaissance").setValue(dateNaissance)
//
//                            Toast.makeText(this, "Compte créé avec succès", Toast.LENGTH_SHORT).show()
//                            val intent = Intent(applicationContext, LoginActivity::class.java)
//                            startActivity(intent)
//                            finish()
//                        }
//                    } else {
//                        // Échec de la création du compte
//                        Toast.makeText(this, "Échec de la création du compte", Toast.LENGTH_SHORT).show()
//                    }
//                }
//        }
//
//
//        //  Aller sur la page connexion
//        btnGoSignIn.setOnClickListener {
//            val intent = Intent(applicationContext, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }


    }
}