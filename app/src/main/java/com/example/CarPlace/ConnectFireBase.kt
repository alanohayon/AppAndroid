package com.example.carplace

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.example.carplace.R

internal class ConnectFireBase : AppCompatActivity() {

    var databaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_login)

        databaseReference.setValue("Connexion success!").addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "Connexion success!", Toast.LENGTH_SHORT).show()
                Log.e("SUCCESS FIREBASE", task.toString())
            } else {
                Toast.makeText(applicationContext, "Echec", Toast.LENGTH_SHORT).show()
                Log.e("ERROR FIREBASE", task.exception.toString())
            }
        }
    }
}