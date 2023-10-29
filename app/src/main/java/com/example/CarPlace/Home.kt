package com.example.CarPlace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.carplace.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class Home : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //objet qui nous permet de nous connecter au SDK de Firebase
        auth = Firebase.auth
        val user = auth.currentUser

        val btnLogout = findViewById(R.id.logout) as Button
        val userDetail = findViewById(R.id.user_detail) as TextView


        //si le user est nul, donc pas connecter le ramener vers la page login
        if (user == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        } else {
            //sinon afficher les details du user
            userDetail.text = user.email
        }

        // si il click sur deconnexion alors la variable session du user sera supprimer et il sera ramené vers la page login
        btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

    }
}