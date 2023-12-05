package com.example.carplace

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carplace.databinding.ActivityCarInfoBinding
import com.example.carplace.databinding.ActivityMapBinding

class CarInfo : AppCompatActivity() {

    private lateinit var binding: ActivityCarInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView = binding.navBar.bottomNavigationView

        bottomNavView.selectedItemId = R.id.nav_car

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> { startActivity(Intent(this, Home::class.java)); true }
                R.id.nav_park -> { startActivity(Intent(this, Map::class.java)); true }
                R.id.nav_car -> { true }
                R.id.nav_account -> { startActivity(Intent(this, UserAccount::class.java)); true }
                else -> false
            }
        }
    }
}