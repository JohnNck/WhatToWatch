package com.example.whattowatch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

class SearchActivity: AppCompatActivity() {
    private lateinit var homeIcon: ImageView
    private lateinit var searchIcon: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        homeIcon = findViewById(R.id.homeIcon)
        searchIcon = findViewById(R.id.searchIcon)

        homeIcon.setOnClickListener {
                // Navigate to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: finish this activity if you don't want to return to it
        }
        if (this is SearchActivity) {
            searchIcon.setImageResource(R.drawable.baseline_search_active)
            ImageViewCompat.setImageTintList(
                searchIcon,
                ContextCompat.getColorStateList(this, R.color.yellow)
            )
        }
        searchIcon.setOnClickListener {
            if (this is SearchActivity) {
                // Do nothing since already in MainActivity
            } else {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                finish() // Optional: finish this activity if you don't want to return to it
            }
        }
    }
}