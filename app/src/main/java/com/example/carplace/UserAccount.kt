package com.example.carplace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carplace.databinding.ActivityHomeBinding
import com.example.carplace.databinding.ActivityUserAccountBinding
import android.content.Intent

import android.widget.RelativeLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


// app/src/main/java/com/example/CarPlace/UserAccount.kt
class UserAccount : AppCompatActivity() {
    private lateinit var binding: ActivityUserAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)



        /* ********** */
        /* NAVIGATION */
        val bottomNavView = binding.navBar.bottomNavigationView
        bottomNavView.selectedItemId = R.id.nav_account // Sélectionne l'icône de compte
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> { startActivity(Intent(this, Home::class.java)); true }
                R.id.nav_park -> { startActivity(Intent(this, Map::class.java)); true }
                R.id.nav_car -> { startActivity(Intent(this, CarInfo::class.java)); true }
                R.id.nav_account -> { true }
                else -> false
            }
        }

        binding.btnAddInfo.setOnClickListener {
            val intent = Intent(this, AddInfoActivity::class.java)
            startActivity(intent)
        }
    }
}
