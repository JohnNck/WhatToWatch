package com.example.whattowatch.activity

import com.example.whattowatch.adapters.SearchMovieAdapter
import com.example.whattowatch.adapters.GenreAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowatch.MyViewModel
import com.example.whattowatch.R
import com.example.whattowatch.adapters.SearchSeriesAdapter
import com.example.whattowatch.response.MovieResults
import com.example.whattowatch.response.SeriesResults
import com.example.whattowatch.util.Credentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search_activity) {

    private val myViewModel: MyViewModel by viewModels()
    private lateinit var movieGenreAdapter: GenreAdapter
    private lateinit var seriesGenreAdapter: GenreAdapter

    private lateinit var searchMovieAdapter: SearchMovieAdapter
    private lateinit var searchSeriesAdapter: SearchSeriesAdapter

    private lateinit var moviesGenresRecyclerView: RecyclerView
    private lateinit var seriesGenresRecyclerView: RecyclerView
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var seriesRecyclerView: RecyclerView

    private lateinit var searchView: SearchView

    private lateinit var discoverMoviesTotalPages: TextView
    private lateinit var discoverSeriesTotalPages: TextView


    private lateinit var previousPageButtonDiscoverMovies: AppCompatButton
    private lateinit var nextPageButtonDiscoverMovies: AppCompatButton

    private lateinit var previousPageButtonDiscoverSeries: AppCompatButton
    private lateinit var nextPageButtonDiscoverSeries: AppCompatButton

    private var discoverMoviesCurrentPage = 1
    private var discoverSeriesCurrentPage = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI elements
        moviesGenresRecyclerView = view.findViewById(R.id.moviesGenresView)
        seriesGenresRecyclerView = view.findViewById(R.id.seriesGenresView)


        nextPageButtonDiscoverMovies = view.findViewById(R.id.nextPageDiscoverMovies)
        previousPageButtonDiscoverMovies = view.findViewById(R.id.previousPageDiscoverMovies)

        nextPageButtonDiscoverSeries = view.findViewById(R.id.nextPageDiscoverSeries)
        previousPageButtonDiscoverSeries = view.findViewById(R.id.previousPageDiscoverSeries)

        discoverMoviesTotalPages = view.findViewById(R.id.totalPagesDiscoverMovies)
        discoverSeriesTotalPages = view.findViewById(R.id.totalPagesDiscoverSeries)
        searchView = view.findViewById(R.id.search_bar)
        moviesRecyclerView = view.findViewById(R.id.moviesSearchView)
        seriesRecyclerView = view.findViewById(R.id.SeriesSearchView)

        // Configure SearchView colors for text and hint
        configureSearchViewColors()

        // Initialize adapters
        movieGenreAdapter = GenreAdapter(emptyList()) { handleGenreSelectionChange() }
        seriesGenreAdapter = GenreAdapter(emptyList()) { handleGenreSelectionChange() }


        // Observe total pages for movies
        myViewModel.discoverMoviesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            discoverMoviesTotalPages.text = "Page $discoverMoviesCurrentPage / $totalPages"
            // Enable or disable pagination buttons based on current page
            nextPageButtonDiscoverMovies.isEnabled = discoverMoviesCurrentPage < totalPages
            previousPageButtonDiscoverMovies.isEnabled = discoverMoviesCurrentPage > 1 // Corrected here
        }

        myViewModel.discoverSeriesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            discoverSeriesTotalPages.text = "Page $discoverSeriesCurrentPage / $totalPages"
            // Enable or disable pagination buttons based on current page
            nextPageButtonDiscoverSeries.isEnabled = discoverSeriesCurrentPage < totalPages
            previousPageButtonDiscoverSeries.isEnabled = discoverSeriesCurrentPage > 1
        }

        setupRecyclerViews()

        // Load genres from ViewModel
        val apiKey = Credentials.API_KEY
        myViewModel.fetchMovieGenres(apiKey)
        myViewModel.fetchSeriesGenres(apiKey)

        // Observe genres and search results
        observeGenres()
        observeSearchResults()
        handleGenreSelectionChange()


        // Pagination button click listeners for Movies
        nextPageButtonDiscoverMovies.setOnClickListener {
            val totalPages = myViewModel.discoverMoviesTotalPages.value ?: 1
            if (discoverMoviesCurrentPage < totalPages) {
                discoverMoviesCurrentPage++
                val selectedMovieGenres = movieGenreAdapter.getSelectedGenres().map { it.id }
                myViewModel.discoverMoviesByGenres(apiKey, discoverMoviesCurrentPage, selectedMovieGenres)
            }
        }

        previousPageButtonDiscoverMovies.setOnClickListener {
            if (discoverMoviesCurrentPage > 1) {
                discoverMoviesCurrentPage--
                val selectedMovieGenres = movieGenreAdapter.getSelectedGenres().map { it.id }
                myViewModel.discoverMoviesByGenres(apiKey, discoverMoviesCurrentPage, selectedMovieGenres)
            }
        }

// Pagination button click listeners for Series
        nextPageButtonDiscoverSeries.setOnClickListener {
            val totalPages = myViewModel.discoverSeriesTotalPages.value ?: 1
            if (discoverSeriesCurrentPage < totalPages) {
                discoverSeriesCurrentPage++
                val selectedSeriesGenres = seriesGenreAdapter.getSelectedGenres().map { it.id }
                myViewModel.discoverSeriesByGenres(apiKey, discoverSeriesCurrentPage, selectedSeriesGenres)
            }
        }

        previousPageButtonDiscoverSeries.setOnClickListener {
            if (discoverSeriesCurrentPage > 1) {
                discoverSeriesCurrentPage--
                val selectedSeriesGenres = seriesGenreAdapter.getSelectedGenres().map { it.id }
                myViewModel.discoverSeriesByGenres(apiKey, discoverSeriesCurrentPage, selectedSeriesGenres)
            }
        }


        // Configure search text listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    // If there's a search query, perform a text-based search
                    myViewModel.searchMovies(apiKey, newText)
                    myViewModel.searchSeries(apiKey, newText)
                } else {

                }
                return true
            }
        })
    }

    private fun configureSearchViewColors() {
        try {
            val searchAutoCompleteField = SearchView::class.java.getDeclaredField("mSearchSrcTextView")
            searchAutoCompleteField.isAccessible = true
            val searchAutoComplete = searchAutoCompleteField.get(searchView) as TextView
            searchAutoComplete.setTextColor(resources.getColor(R.color.white, null))
            searchAutoComplete.setHintTextColor(resources.getColor(R.color.white, null))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupRecyclerViews() {
        // Genre RecyclerViews
        moviesGenresRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        moviesGenresRecyclerView.adapter = movieGenreAdapter

        seriesGenresRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        seriesGenresRecyclerView.adapter = seriesGenreAdapter

        // Movies and Series RecyclerViews
        searchMovieAdapter = SearchMovieAdapter { movie -> navigateToMovieDetails(movie) }
        searchSeriesAdapter = SearchSeriesAdapter { series -> navigateToSeriesDetails(series) }

        moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        moviesRecyclerView.adapter = searchMovieAdapter

        seriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        seriesRecyclerView.adapter = searchSeriesAdapter
    }

    private fun observeGenres() {
        // Observe genre lists
        myViewModel.movieGenres.observe(viewLifecycleOwner) { genres ->
            movieGenreAdapter.updateGenres(genres)
        }
        myViewModel.seriesGenres.observe(viewLifecycleOwner) { genres ->
            seriesGenreAdapter.updateGenres(genres)
        }
    }

    private fun observeSearchResults() {
        // Observe movie and series search results
        myViewModel.movieSearchResults.observe(viewLifecycleOwner) { movies ->
            searchMovieAdapter.updateSearchResults(movies ?: emptyList())
        }
        myViewModel.seriesSearchResults.observe(viewLifecycleOwner) { series ->
            searchSeriesAdapter.updateSeriesResults(series ?: emptyList())
        }

        // Observe genre-based discovery results
        myViewModel.movieDiscoverResults.observe(viewLifecycleOwner) { movies ->
            searchMovieAdapter.updateSearchResults(movies)
        }
        myViewModel.seriesDiscoverResults.observe(viewLifecycleOwner) { series ->
            searchSeriesAdapter.updateSeriesResults(series)
        }
    }

    private fun handleGenreSelectionChange() {
        val selectedMovieGenreIds = movieGenreAdapter.getSelectedGenres().map { it.id }
        val selectedSeriesGenreIds = seriesGenreAdapter.getSelectedGenres().map { it.id }

        val apiKey = Credentials.API_KEY
        if (selectedMovieGenreIds.isNotEmpty()) {
            myViewModel.discoverMoviesByGenres(apiKey,discoverMoviesCurrentPage,selectedMovieGenreIds)
        } else {
            searchMovieAdapter.updateSearchResults(emptyList()) // Clear movies if no genres are selected
        }

        if (selectedSeriesGenreIds.isNotEmpty()) {
            myViewModel.discoverSeriesByGenres(apiKey,discoverSeriesCurrentPage, selectedSeriesGenreIds)
        } else {
            searchSeriesAdapter.updateSeriesResults(emptyList()) // Clear series if no genres are selected
        }
    }

    private fun navigateToMovieDetails(movie: MovieResults) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java).apply {
            putExtra("id", movie.movieId)
            putExtra("title", movie.title)
            putExtra("overview", movie.overView)
            putExtra("poster_path", movie.posterPath)
            putExtra("vote_average", movie.voteAverage)
        }
        startActivity(intent)
    }

    private fun navigateToSeriesDetails(series: SeriesResults) {
        val intent = Intent(requireContext(), SeriesDetailsActivity::class.java).apply {
            putExtra("id", series.seriesId)
            putExtra("name", series.name)
            putExtra("overview", series.overView)
            putExtra("poster_path", series.posterPath)
            putExtra("vote_average", series.voteAverage)
        }
        startActivity(intent)
    }
}
