package com.example.carplace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carplace.databinding.ActivityMapBinding
import com.example.carplace.databinding.ActivityUserAccountBinding
import kotlin.collections.Map

class Map : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView = binding.navBar.bottomNavigationView

        bottomNavView.selectedItemId = R.id.nav_park

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> { startActivity(Intent(this, Home::class.java)); true }
                R.id.nav_park -> { true }
                R.id.nav_car -> { startActivity(Intent(this, CarInfo::class.java)); true }
                R.id.nav_account -> { startActivity(Intent(this, UserAccount::class.java)); true }
                else -> false
            }
        }
    }
}
