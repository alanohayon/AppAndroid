package com.example.carplace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class User : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // Bouton Modifier Adresse e-mail
        val btnEditEmail = findViewById<Button>(R.id.btnEditEmail)
        btnEditEmail.setOnClickListener {
            // Ajoutez votre logique pour modifier l'adresse e-mail ici
        }

        // Bouton Modifier Prénom
        val btnEditFirstName = findViewById<Button>(R.id.btnEditFirstName)
        btnEditFirstName.setOnClickListener {
            // Ajoutez votre logique pour modifier le prénom ici
        }

        // Bouton Modifier Nom
        val btnEditLastName = findViewById<Button>(R.id.btnEditLastName)
        btnEditLastName.setOnClickListener {
            // Ajoutez votre logique pour modifier le nom ici
        }

        // Bouton Modifier Mail
        val btnEditMail = findViewById<Button>(R.id.btnEditMail)
        btnEditMail.setOnClickListener {
            // Ajoutez votre logique pour modifier le mail ici
        }

        // Bouton Modifier Mot de passe
        val btnEditPassword = findViewById<Button>(R.id.btnEditPassword)
        btnEditPassword.setOnClickListener {
            // Ajoutez votre logique pour modifier le mot de passe ici
        }
    }
}
