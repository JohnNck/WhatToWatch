package com.example.whattowatch.activity

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whattowatch.MyViewModel
import com.example.whattowatch.R
import com.example.whattowatch.util.Credentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesDetailsActivity : AppCompatActivity() {
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_details)

        val seriesTrailer: WebView = findViewById(R.id.seriesTrailer)
        val seriesTitle: TextView = findViewById(R.id.seriesNameText)
        val seriesTrailerName : TextView = findViewById(R.id.seriesTrailerName)
        val seriesOverview: TextView = findViewById(R.id.seriesSummary)
        val seriesPoster: ImageView = findViewById(R.id.posterSeries)
        val seriesScore: TextView = findViewById(R.id.score)
        val apiKey = Credentials.API_KEY
        val name = intent.getStringExtra("name")
        val overview = intent.getStringExtra("overview")
        val poster = intent.getStringExtra("poster_path")
        val score = intent.getFloatExtra("vote_average", 0.0f)
        val seriesId = intent.getIntExtra("id", -1)
        seriesTitle.text = name
        seriesOverview.text = overview
        seriesScore.text = String.format("%.1f/10", score)
        seriesTrailerName.text = "$name Trailer"

        val fullPosterUrl = "https://image.tmdb.org/t/p/w500$poster"
        Glide.with(this)
            .load(fullPosterUrl)
            .error(R.drawable.image_not_supported)
            .into(seriesPoster)

        val backArrow: ImageView = findViewById(R.id.backArrow)
        backArrow.setOnClickListener {
            finish()
        }
        setupWebView(seriesTrailer)
        fetchSeriesTrailer(seriesId, apiKey)
    }

    private fun setupWebView(seriesTrailer: WebView) {
        seriesTrailer.settings.javaScriptEnabled = true
        seriesTrailer.settings.mediaPlaybackRequiresUserGesture = false
        seriesTrailer.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        seriesTrailer.webViewClient = WebViewClient()

        myViewModel.seriesTrailerKey.observe(this) { trailerKey ->
            if (!trailerKey.isNullOrEmpty()) {
                val youtubeUrl = "https://www.youtube.com/embed/$trailerKey"
                seriesTrailer.loadUrl(youtubeUrl)
            } else {
                seriesTrailer.loadData("<h3>No trailer available</h3>", "text/html", "UTF-8")
            }
        }
    }
    private fun fetchSeriesTrailer(seriesId: Int, apiKey: String) {
        myViewModel.fetchSeriesTrailer(seriesId, apiKey)
    }
}
