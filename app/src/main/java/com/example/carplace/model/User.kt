package com.example.carplace.model

import android.util.Log
import com.example.carplace.Database
import com.google.firebase.database.DatabaseReference
import java.util.*

class User(
    var id: String = "",
    private var firstName: String = "",
    private var lastName: String = "",
    private var email: String = "",
    private var password: String = "",
    private var date: String = "",
    private var phone: Int = 0,
    private var city: String = ""
) {
    private val database: Database = Database

    private val userEntity: DatabaseReference = database.userEntity().child(id)

    fun updateUserInDatabase() {
        // Mettez à jour les informations de l'utilisateur dans la base de données si nécessaire
    }

    fun deleteUserFromDatabase() {
        // Supprimez l'utilisateur de la base de données si nécessaire
    }

    companion object {
        fun getUserById(database: Database, userId: String): User {
            // Obtenez les informations de l'utilisateur à partir de la base de données
            // Vous pouvez retourner un nouvel objet User avec ces informations
            return User("userId", "John", "Doe", "john@example.com", "password123", "2023-01-01", 123456789, "City")
        }
    }
}
