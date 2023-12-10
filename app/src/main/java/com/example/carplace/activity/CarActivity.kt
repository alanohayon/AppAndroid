package com.example.carplace.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carplace.activity.databinding.ActivityCarBinding

class CarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_car
        Utils.setupMenu(bottomNavView)

//        bottomNavView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_search -> { startActivity(Intent(this, HomeActivity::class.java)); true }
//                R.id.nav_park -> { startActivity(Intent(this, MapActivity::class.java)); true }
//                R.id.nav_car -> { true }
//                R.id.nav_account -> { startActivity(Intent(this, UserAccountActivity::class.java)); true }
//                else -> false
//            }
//        }
    }
}