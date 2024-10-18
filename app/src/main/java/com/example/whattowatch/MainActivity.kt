package com.example.whattowatch

import MovieAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattowatch.response.MoviePopularResults
import com.example.whattowatch.util.Credentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var movieName: TextView
    private lateinit var buttonSearch: Button
    private lateinit var moviePoster: ImageView
    private lateinit var homeIcon: ImageView
    private lateinit var searchIcon: ImageView
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var bestMoviesRecyclerView: RecyclerView
    private lateinit var nowPlayingMoviesView: RecyclerView
    private lateinit var popularMovieAdapter: MovieAdapter
    private lateinit var bestMovieAdapter: MovieAdapter
    private lateinit var nowPlayingMovieAdapter: MovieAdapter
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        popularMoviesRecyclerView = findViewById(R.id.popularMoviesView)
        bestMoviesRecyclerView = findViewById(R.id.bestMoviesView)
        nowPlayingMoviesView = findViewById(R.id.nowPlayingMoviesView)
        homeIcon = findViewById(R.id.homeIcon)
        searchIcon = findViewById(R.id.searchIcon)
        if (this is MainActivity) {
            homeIcon.setImageResource(R.drawable.baseline_home_active)
            ImageViewCompat.setImageTintList(
                homeIcon,
                ContextCompat.getColorStateList(this, R.color.yellow)
            )
        }
            homeIcon.setOnClickListener {
            if (this is MainActivity) {
                homeIcon.setImageResource(R.drawable.baseline_home_active)
                ImageViewCompat.setImageTintList(
                    homeIcon,
                    ContextCompat.getColorStateList(this, R.color.yellow))
            } else {
                // Navigate to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional: finish this activity if you don't want to return to it
            }
        }
        searchIcon.setOnClickListener {
            // Navigate to SearchActivity
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            finish() // Optional: finish this activity if you don't want to return to it
        }

        // Set up LayoutManagers for each RecyclerView
        popularMoviesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bestMoviesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        nowPlayingMoviesView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Observe popularResults LiveData and update the RecyclerView
        myViewModel.popularResults.observe(this, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                popularMoviesRecyclerView.adapter = MovieAdapter(movieList) { movie ->
                    navigateToDetails(movie)
                }
            }
        })

        // Observe bestMoviesResults LiveData and update the RecyclerView
        myViewModel.bestMoviesResults.observe(this, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                bestMoviesRecyclerView.adapter = MovieAdapter(movieList) { movie ->
                    navigateToDetails(movie)
                }
            }
        })

        // Observe nowPlayingMovies LiveData and update the RecyclerView
        myViewModel.nowPlayingMoviesResults.observe(this, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                nowPlayingMoviesView.adapter = MovieAdapter(movieList) { movie ->
                    navigateToDetails(movie)
                }
            }
        })

        // Fetch movies
        val apiKey = Credentials.API_KEY
        fetchPopularMovies(apiKey)
        fetchBestMovies(apiKey)
        fetchNowPlayingMovies(apiKey)
    }

    private fun navigateToDetails(movie: MoviePopularResults) {
        val intent = Intent(this, MovieDetailsActivity::class.java).apply {
            putExtra("id", movie.movieId)    // Pass movie ID
            putExtra("title", movie.title)         // Pass movie title
            putExtra("overview", movie.overView)   // Pass movie overview
            putExtra("poster_path", movie.posterPath)   // Pass movie poster
            putExtra("vote_average", movie.voteAverage)   // Pass movie score
        }
        startActivity(intent)
    }

    private fun fetchPopularMovies(apiKey: String) {
        myViewModel.fetchPopularMovies(apiKey)
    }

    private fun fetchBestMovies(apiKey: String) {
        myViewModel.fetchBestMovies(apiKey)
    }

    private fun fetchNowPlayingMovies(apiKey: String) {
        myViewModel.fetchNowPlayingMovies(apiKey)
    }
}