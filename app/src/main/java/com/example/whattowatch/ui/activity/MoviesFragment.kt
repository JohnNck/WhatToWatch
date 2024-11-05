package com.example.whattowatch.ui.activity

import com.example.whattowatch.adapters.MovieAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowatch.ui.MyViewModel
import com.example.whattowatch.R
import com.example.whattowatch.response.MovieResults
import com.example.whattowatch.util.Credentials
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.activity_main) {
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var bestMoviesRecyclerView: RecyclerView
    private lateinit var nowPlayingMoviesView: RecyclerView
    private lateinit var upcomingMoviesView: RecyclerView

    private lateinit var nowPlayingMoviesTotalPages: TextView
    private lateinit var bestMoviesTotalPages: TextView
    private lateinit var popularMoviesTotalPages: TextView


    private lateinit var previousPageButtonNowPlayingMovies: AppCompatButton
    private lateinit var nextPageButtonNowPlayingMovies: AppCompatButton

    private lateinit var previousPageButtonBestMovies: AppCompatButton
    private lateinit var nextPageButtonBestMovies: AppCompatButton

    private lateinit var previousPageButtonPopularMovies: AppCompatButton
    private lateinit var nextPageButtonPopularMovies: AppCompatButton


    private var nowPlayingMoviesCurrentPage = 1
    private var bestMoviesCurrentPage = 1
    private var popularMoviesCurrentPage = 1
    private var upcomingMoviesCurrentPage = 1
    private val myViewModel: MyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularMoviesRecyclerView = view.findViewById(R.id.popularMoviesView)
        bestMoviesRecyclerView = view.findViewById(R.id.bestMoviesView)
        nowPlayingMoviesView = view.findViewById(R.id.nowPlayingMoviesView)
        upcomingMoviesView = view.findViewById(R.id.upComingMovies)

        nowPlayingMoviesTotalPages = view.findViewById(R.id.totalPagesNowPlayingMovies)
        bestMoviesTotalPages = view.findViewById(R.id.totalPagesBestMovies)
        popularMoviesTotalPages = view.findViewById(R.id.totalPagesPopularMovies)

        previousPageButtonNowPlayingMovies = view.findViewById(R.id.previousPageNowPlayingMovies)
        nextPageButtonNowPlayingMovies = view.findViewById(R.id.nextPageNowPlayingMovies)

        previousPageButtonBestMovies = view.findViewById(R.id.previousPageBestMovies)
        nextPageButtonBestMovies = view.findViewById(R.id.nextPageBestMovies)

        previousPageButtonPopularMovies = view.findViewById(R.id.previousPagePopularMovies)
        nextPageButtonPopularMovies = view.findViewById(R.id.nextPagePopularMovies)

        popularMoviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bestMoviesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        nowPlayingMoviesView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        upcomingMoviesView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)



        myViewModel.nowPlayingMoviesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            nowPlayingMoviesTotalPages.text = "Page $nowPlayingMoviesCurrentPage/$totalPages"
            // Enable or disable pagination buttons based on current page
            nextPageButtonNowPlayingMovies.isEnabled = nowPlayingMoviesCurrentPage < totalPages
            previousPageButtonNowPlayingMovies.isEnabled = nowPlayingMoviesCurrentPage > 1

        }
        myViewModel.bestMoviesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            bestMoviesTotalPages.text = "Page $bestMoviesCurrentPage/$totalPages"
            // Enable or disable pagination buttons based on current page
            nextPageButtonBestMovies.isEnabled = bestMoviesCurrentPage < totalPages
            previousPageButtonBestMovies.isEnabled = bestMoviesCurrentPage > 1

        }
        myViewModel.popularMoviesTotalPages.observe(viewLifecycleOwner) { totalPages ->
            popularMoviesTotalPages.text = "Page $popularMoviesCurrentPage/$totalPages"
            // Enable or disable pagination buttons based on current page
            if (totalPages>500){
                popularMoviesTotalPages.text = "Page $popularMoviesCurrentPage/500"
            }
            nextPageButtonPopularMovies.isEnabled = popularMoviesCurrentPage < totalPages
            previousPageButtonPopularMovies.isEnabled = popularMoviesCurrentPage > 1

        }

        myViewModel.popularResults.observe(viewLifecycleOwner, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                popularMoviesRecyclerView.adapter = MovieAdapter(movieList) { movie ->
                    navigateToDetails(movie)
                }
            }
        })

        myViewModel.bestMoviesResults.observe(viewLifecycleOwner, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                bestMoviesRecyclerView.adapter = MovieAdapter(movieList) { movie ->
                    navigateToDetails(movie)
                }
            }
        })

        myViewModel.nowPlayingMoviesResults.observe(viewLifecycleOwner, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                nowPlayingMoviesView.adapter = MovieAdapter(movieList) { movie ->
                    navigateToDetails(movie)
                }
            }
        })
        myViewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { movieList ->
            if (movieList.isNotEmpty()) {
                upcomingMoviesView.adapter = MovieAdapter(movieList) { movie ->
                    navigateToDetails(movie)
                }
            }
        })

        val apiKey = Credentials.API_KEY
        fetchPopularMovies(apiKey,popularMoviesCurrentPage)
        fetchBestMovies(apiKey,bestMoviesCurrentPage)
        fetchNowPlayingMovies(apiKey,nowPlayingMoviesCurrentPage)
        fetchUpcomingMovies(apiKey,upcomingMoviesCurrentPage)


        nextPageButtonNowPlayingMovies.setOnClickListener {
            if (nowPlayingMoviesCurrentPage < (myViewModel.nowPlayingMoviesTotalPages.value ?: 1)) {
                nowPlayingMoviesCurrentPage++
                myViewModel.fetchNowPlayingMovies(apiKey,nowPlayingMoviesCurrentPage)
            }
        }
        previousPageButtonNowPlayingMovies.setOnClickListener {
            if (nowPlayingMoviesCurrentPage > 1) {
                nowPlayingMoviesCurrentPage--
                myViewModel.fetchNowPlayingMovies(apiKey,nowPlayingMoviesCurrentPage)
            }
        }


        nextPageButtonBestMovies.setOnClickListener {
            if (bestMoviesCurrentPage < (myViewModel.bestMoviesTotalPages.value ?: 1)) {
                bestMoviesCurrentPage++
                myViewModel.fetchBestMovies(apiKey,bestMoviesCurrentPage)
            }
        }
        previousPageButtonBestMovies.setOnClickListener {
            if (bestMoviesCurrentPage > 1) {
                bestMoviesCurrentPage--
                myViewModel.fetchBestMovies(apiKey,bestMoviesCurrentPage)
            }
        }

        nextPageButtonPopularMovies.setOnClickListener {
            if (popularMoviesCurrentPage < (myViewModel.popularMoviesTotalPages.value ?: 1)) {
                popularMoviesCurrentPage++
                myViewModel.fetchPopularMovies(apiKey,popularMoviesCurrentPage)
            }
        }
        previousPageButtonPopularMovies.setOnClickListener {
            if (popularMoviesCurrentPage > 1) {
                popularMoviesCurrentPage--
                myViewModel.fetchPopularMovies(apiKey,popularMoviesCurrentPage)
            }
        }
    }

    private fun navigateToDetails(movie: MovieResults) {
        val intent = Intent(requireContext(), MovieDetailsActivity::class.java).apply {
            putExtra("id", movie.movieId)
            putExtra("title", movie.title)
            putExtra("overview", movie.overView)
            putExtra("poster_path", movie.posterPath)
            putExtra("vote_average", movie.voteAverage)
        }
        startActivity(intent)
    }
    private fun fetchPopularMovies(apiKey: String, currentPage:Int) {
        myViewModel.fetchPopularMovies(apiKey,currentPage)
    }

    private fun fetchBestMovies(apiKey: String, currentPage:Int) {
        myViewModel.fetchBestMovies(apiKey,currentPage)
    }

    private fun fetchNowPlayingMovies(apiKey: String, currentPage:Int) {
        myViewModel.fetchNowPlayingMovies(apiKey,currentPage)
    }
    private fun fetchUpcomingMovies(apiKey: String, currentPage:Int){
        myViewModel.fetchUpcomingMovies(apiKey,currentPage)
    }
}
