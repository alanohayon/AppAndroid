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

        val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
        database = Firebase.database(databaseUrl).reference


        binding.btnSave.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()

            // Obtention de l'identifiant de l'utilisateur actuellement connecté
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener

            // Enregistrement des informations de l'utilisateur dans la base de données
            // Donc pour la "branche de données" je choisi d'abord de le mettre dans une
            // branche que je nomme "user" avec child, puis dans la branche de l'id du
            // l'utilisateur courrant, puis les champs firstName et lastName
            val newPlaceRef = database.child("places").push()
            newPlaceRef.child("lat").setValue(48.8471)
            newPlaceRef.child("lng").setValue(2.2891)

//            database.child("places").child("id").child("lat").setValue(48.8566)
//            database.child("places").child("id").child("lng").setValue(48.8566)

            Toast.makeText(this, "Informations enregistrées", Toast.LENGTH_SHORT).show()
            finish()
        }

//        firebaseDatabase = FirebaseDatabase.getInstance()
//        databaseReference = firebaseDatabase?.getReference("users")
//
        val userDetail = binding.userDetail
        val btnLogout = binding.logout
        val btnGestionUser = binding.btnGestionUser

//        // Connexion à l'authent de firebase
        auth = Firebase.auth
        val currentUser = auth.currentUser
        userDetail.text = currentUser?.email
//        // recuperer les info du user
//        binding.btnSave.setOnClickListener {
//
//
//            Toast.makeText(this, "1", Toast.LENGTH_LONG).show()

//            val userId = FirebaseAuth.getInstance().currentUser!!.uid
//            val firstName = binding.firstNameEditText.text.toString()
//            val lastName = binding.lastNameEditText.text.toString()
//            val email = binding.emailEditText.text.toString()
//            val phoneNumber = binding.phoneEditText.text.toString().toInt()
//            val date = "11/03/2002"
//            val password = binding.passwordEditText.text.toString()
//            val city = binding.cityEditText.text.toString()
//
//            val user = User("userId", "firstName", "lastName", "email", "password", "date", 123456, "city")
//
//            Toast.makeText(this, "2", Toast.LENGTH_LONG).show()
//            databaseReference?.child("212")?.setValue("testout")
////                .addOnCompleteListener {
////                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
////                }.addOnFailureListener { err ->
////                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
////                }
//
//            Toast.makeText(this, "3", Toast.LENGTH_LONG).show()
//


//        }


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
