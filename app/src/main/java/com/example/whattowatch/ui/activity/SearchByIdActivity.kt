package com.example.whattowatch.ui.activity

import com.example.whattowatch.adapters.MovieAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowatch.ui.MyViewModel
import com.example.whattowatch.R
import com.example.whattowatch.response.MovieModel
import com.example.whattowatch.util.Credentials
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchByIdActivity : AppCompatActivity() {

    private lateinit var movieId: TextView
    private lateinit var movieName: TextView
    private lateinit var movieOverView : TextView
    private lateinit var buttonSearch: Button
    private lateinit var moviePoster : ImageView
    private lateinit var buttonChange : Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.id_search)


        movieId = findViewById(R.id.giveNumber)
        movieName = findViewById(R.id.movieName)
        buttonSearch = findViewById(R.id.button)
        movieOverView = findViewById(R.id.overView)
        moviePoster = findViewById(R.id.moviePoster)
        buttonChange = findViewById(R.id.mainActivityGo)

        myViewModel.movie.observe(this, Observer { movie ->
            if (movie != null) {
                updateUI(movie)
            } else {
                movieName.text = "Movie not found"
            }
        })
        movieId.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {

                hideKeyboard()
                true
            } else {
                false
            }

        }
        buttonChange.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonSearch.setOnClickListener {
            val apiKey = Credentials.API_KEY
            val movieIdValue = movieId.text.toString().toIntOrNull()
            if (movieIdValue != null) {
                myViewModel.fetchMovie(movieIdValue , apiKey )
            } else {
                movieName.text = "Invalid Movie ID"
            }
        }
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(movieId.windowToken, 0)
    }
    private fun updateUI(movie: MovieModel) {
        movieOverView.text = movie.movieOverview
        movieName.text = movie.title
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val posterUrl = baseUrl + movie.posterPath
        Log.d("MainActivity", "Poster URL: ${movie.posterPath}")

        Picasso.get()
            .load(posterUrl)
            .error(R.drawable.error_image)
            .into(moviePoster)
    }
}
