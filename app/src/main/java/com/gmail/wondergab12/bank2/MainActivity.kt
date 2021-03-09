package com.gmail.wondergab12.bank2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MapFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        mapFragment ?: showMapFragment()
    }

    private fun showMapFragment() {
        val mapFragment: MapFragment = MapFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, mapFragment)
            .commit()
    }
}
