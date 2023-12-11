package com.example.carplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carplace.databinding.ActivityAddInfoBinding
import com.google.firebase.database.DatabaseReference
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddInfoBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /* ******************************************** */
        /* Connexion à la Realtime Database spécifique */
        val databaseUrl = "https://carplace-76342-default-rtdb.europe-west1.firebasedatabase.app"
        database = Firebase.database(databaseUrl).reference


        /* ****************** */
        /* Bouton ENREGISTER */
        binding.btnSave.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()

            // Obtention de l'identifiant de l'utilisateur actuellement connecté
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setOnClickListener

            // Enregistrement des informations de l'utilisateur dans la base de données
            // Donc pour la "branche de données" je choisi d'abord de le mettre dans une
            // branche que je nomme "user" avec child, puis dans la branche de l'id du
            // l'utilisateur courrant, puis les champs firstName et lastName
            database.child("users").child(userId).child("firstName").setValue(firstName)
            database.child("users").child(userId).child("lastName").setValue(lastName)

            Toast.makeText(this, "Informations enregistrées", Toast.LENGTH_SHORT).show()
            finish()
        }


        /* *************** */
        /* Bouton ANNULER */
        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}

