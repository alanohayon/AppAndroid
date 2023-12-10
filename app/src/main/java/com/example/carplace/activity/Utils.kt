package com.example.carplace.activity

import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

object Utils {

    fun setupMenu(bottomNavView: BottomNavigationView) {

        bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_map -> {
                    if (bottomNavView.context !is MapActivity)
                    startActivity(bottomNavView.context, Intent(bottomNavView.context, MapActivity::class.java));true
                }
                R.id.nav_park -> {
                    if (bottomNavView.context !is ParkActivity)
                    startActivity(bottomNavView.context, Intent(bottomNavView.context, ParkActivity::class.java));true
                }
                R.id.nav_car -> {
                    if (bottomNavView.context !is CarActivity)
                    startActivity(bottomNavView.context, Intent(bottomNavView.context,CarActivity::class.java));true
                }
                R.id.nav_account -> {
                    if (bottomNavView.context !is UserAccountActivity)
                    startActivity(bottomNavView.context, Intent(bottomNavView.context,UserAccountActivity::class.java));true
                }
                else -> false
            }
        }
    }

    private fun startActivity(context: Context, intent: Intent) {
        // Utilisez le contexte de la vue de la barre de navigation
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


}