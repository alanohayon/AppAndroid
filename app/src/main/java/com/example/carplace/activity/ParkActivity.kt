package com.example.carplace.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carplace.activity.databinding.ActivityParkBinding


class ParkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NAVIGATION BAR
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_park
        Utils.setupMenu(bottomNavView)
    }
}