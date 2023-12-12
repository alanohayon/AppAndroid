//package com.example.carplace.activity
//
//import android.content.Context
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import com.example.carplace.activity.databinding.ActivityInfoMarkerBinding
//import com.example.carplace.activity.databinding.ActivityMapBinding
//import com.example.carplace.model.Place
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.model.Marker
//
//class InfoMarker(private val context: Context) : GoogleMap.InfoWindowAdapter {
//
//    val binding = ActivityInfoMarkerBinding.inflate(LayoutInflater.from(context))
//
//    override fun getInfoContents(marker: Marker?): View? {
//        // 1. Get tag
//        val place = marker?.tag as? Place ?: return null
//
//        // 2. Set values on the existing binding instance
//        binding.distance.text = place.distance.toString()
//        binding.goTo.text = place.goTo
//        binding.takePlace.text = place.takePlace
//
//        // 3. Return the root view of the binding
//        return binding.root
//    }
//
//    override fun getInfoWindow(marker: Marker?): View? {
//        // Return null to indicate that the
//        // default window (white bubble) should be used
//        return null
//    }
//}