package com.example.whattowatch

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whattowatch.util.Credentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieTrailer: WebView = findViewById(R.id.movieTrailer)
        val movieTitle: TextView = findViewById(R.id.movieNameText)
        val movieTrailerName : TextView = findViewById(R.id.movieTrailerName)
        val movieOverview: TextView = findViewById(R.id.movieSummary)
        val moviePoster: ImageView = findViewById(R.id.posterMovie)
        val movieScore: TextView = findViewById(R.id.score)
        val apiKey = Credentials.API_KEY

        // Retrieve data from Intent extras
        val title = intent.getStringExtra("title")
        val overview = intent.getStringExtra("overview")
        val poster = intent.getStringExtra("poster_path")
        val score = intent.getFloatExtra("vote_average", 0.0f)
        val movieId = intent.getIntExtra("id", -1)
        // Set data to views
        movieTitle.text = title
        movieOverview.text = overview
        movieScore.text = String.format("%.1f/10", score)
        movieTrailerName.text = "$title Trailer"

        val fullPosterUrl = "https://image.tmdb.org/t/p/w500$poster"  // URL for TMDB poster
        Glide.with(this)
            .load(fullPosterUrl)
            .error(R.drawable.error_image) // Optional error image
            .into(moviePoster)

        val backArrow: ImageView = findViewById(R.id.backArrow)

        // Set a click listener to finish the activity
        backArrow.setOnClickListener {
            finish() // Closes the current activity and goes back
        }

        // Setup WebView
        setupWebView(movieTrailer)

        // Fetch the trailer and observe the LiveData for the trailer key
        fetchMovieTrailer(movieId, apiKey)
    }

    private fun setupWebView(movieTrailer: WebView) {
        // Enable JavaScript and allow mixed content
        movieTrailer.settings.javaScriptEnabled = true // Enable JavaScript for YouTube
        movieTrailer.settings.mediaPlaybackRequiresUserGesture = false // Allow auto-play of videos
        movieTrailer.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        movieTrailer.webViewClient = WebViewClient() // Prevent opening in an external browser

        // Observe trailer key changes
        myViewModel.movieTrailerKey.observe(this) { trailerKey ->
            if (!trailerKey.isNullOrEmpty()) {
                // Use the correct embed URL format
                val youtubeUrl = "https://www.youtube.com/embed/$trailerKey"
                movieTrailer.loadUrl(youtubeUrl) // Load the YouTube embed URL
            } else {
                // Handle the case where the trailer key is null or empty
                movieTrailer.loadData("<h3>No trailer available</h3>", "text/html", "UTF-8")
            }
        }
    }

    private fun fetchMovieTrailer(movieId: Int, apiKey: String) {
        myViewModel.fetchMovieTrailer(movieId, apiKey)
    }
}
