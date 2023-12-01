package com.example.CarPlace


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.CarPlace.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class Home : AppCompatActivity() {

    //  Variable binding qui permet de recuperer les ref de la page activity_main.xml
    private lateinit var binding: ActivityHomeBinding

    // Connexion à l'authent de firebase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Initialisez le binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Objet qui nous permet de nous connecter à Firebase
        auth = Firebase.auth

        //  Verifier si il y a un user connecté recement
        val user = auth.currentUser

        val btnLogout = binding.btnLogout

        //  Si le user est nul, donc pas connecter le ramener vers la page login
        if (user == null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            //  Sinon afficher les details du user
            binding.userDetail.text = user.email
        }

        //  Si il click sur deconnexion alors la variable session du user sera supprimer et il sera ramené vers la page login
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
            finish()
        }


    }

}