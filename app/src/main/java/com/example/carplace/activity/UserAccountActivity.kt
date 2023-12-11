package com.example.carplace.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carplace.activity.databinding.ActivityUserAccountBinding
import android.content.Intent
import android.widget.Toast
import com.example.carplace.model.User

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class UserAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserAccountBinding
    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onStart() {
        super.onStart()

        // Connexion à l'authent de firebase
        auth = Firebase.auth

        //  On recupere le user courant (comme qd on verifier que la variable session existe)
        val currentUser = auth.currentUser

        //si le user est nul, donc pas connecter le ramener vers la page login
        if (currentUser == null) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userDetail = binding.userDetail
        val btnLogout = binding.logout
        val btnGestionUser = binding.btnGestionUser

        database = Firebase.database.getReference("carplace-db")


        // Connexion à l'authent de firebase
        auth = Firebase.auth
        val currentUser = auth.currentUser
        userDetail.text = currentUser?.email

        // recuperer les info du user
        binding.btnSave.setOnClickListener {

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phoneNumber = binding.phoneEditText.text.toString().toInt()
            val date = "11/03/2002"
            val password = binding.passwordEditText.text.toString()
            val city = binding.cityEditText.text.toString()

//            userEntity.child("id").setValue(userId)
//            userEntity.child("firstName").setValue(firstName)
//            userEntity.child("lastName").setValue(lastName)

//            Ref.child("Car")
//            val test = userRef.child("firstName").setValue(firstName)
//            userRef.child(userId).child("lastName").setValue(lastName)

//            val user = User(userId, firstName, lastName, email, password, date, phoneNumber,  city)
//            user.addUserToDatabase()

//            if (test.isSuccessful) {
//                Toast.makeText(this, "Informations enregistrées", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show()
//            }

            database.child("users").child("papapa").setValue("peedeeze")


        }


        // si il click sur deconnexion alors la variable session du user sera supprimer et il sera ramené vers la page login
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_account
        Utils.setupMenu(bottomNavView)
    }
}
