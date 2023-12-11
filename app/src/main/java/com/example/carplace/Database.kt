package com.example.carplace

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object Database {
    private val databaseReference: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().getReference("carplace-db")
    }

    fun userEntity(): DatabaseReference {
        return databaseReference.child("USER")
    }
}