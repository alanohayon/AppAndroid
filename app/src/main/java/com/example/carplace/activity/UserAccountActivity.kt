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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase



class UserAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserAccountBinding
    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

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

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("users")

        val userDetail = binding.userDetail
        val btnLogout = binding.logout

        // Connexion à l'authent de firebase
        auth = Firebase.auth
        val currentUser = auth.currentUser
        userDetail.text = currentUser?.email
        // recuperer les info du user
        binding.btnSave.setOnClickListener {


            Toast.makeText(this, "1", Toast.LENGTH_LONG).show()

//            val userId = FirebaseAuth.getInstance().currentUser!!.uid
//            val firstName = binding.firstNameEditText.text.toString()
//            val lastName = binding.lastNameEditText.text.toString()
//            val email = binding.emailEditText.text.toString()
//            val phoneNumber = binding.phoneEditText.text.toString().toInt()
//            val date = "11/03/2002"
//            val password = binding.passwordEditText.text.toString()
//            val city = binding.cityEditText.text.toString()

            val user = User("userId", "firstName", "lastName", "email", "password", "date", 123456, "city")

            Toast.makeText(this, "2", Toast.LENGTH_LONG).show()
            databaseReference?.child("212")?.setValue("testout")
//                .addOnCompleteListener {
//                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
//                }.addOnFailureListener { err ->
//                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//                }

            Toast.makeText(this, "3", Toast.LENGTH_LONG).show()



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
