package com.example.whattowatch.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whattowatch.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MoviesFragment())
                .commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeMenu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, MoviesFragment())
                        .commit()
                    true
                }
                R.id.tvSeriesMenu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, TvSeriesFragment())
                        .commit()
                    true
                }
                R.id.searchMenu -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}