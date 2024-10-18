// MyViewModel.kt
package com.example.whattowatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whattowatch.domain.repository.MyRepository
import com.example.whattowatch.response.MovieModel
import com.example.whattowatch.response.MoviePopularResponse
import com.example.whattowatch.response.MoviePopularResults
import com.example.whattowatch.response.MovieTrailerResponse
import com.example.whattowatch.response.MovieTrailerResults
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class  MyViewModel @Inject constructor(
    private val repository: MyRepository
) : ViewModel() {

    private val _movie = MutableLiveData<MovieModel?>()
    val movie: LiveData<MovieModel?> = _movie

    private val _popularResults = MutableLiveData<List<MoviePopularResults>>()
    val popularResults: LiveData<List<MoviePopularResults>> = _popularResults

    private val _bestMoviesResults = MutableLiveData<List<MoviePopularResults>>()
    val bestMoviesResults: LiveData<List<MoviePopularResults>> = _bestMoviesResults

    private val _nowPlayingMoviesResults = MutableLiveData<List<MoviePopularResults>>()
    val nowPlayingMoviesResults: LiveData<List<MoviePopularResults>> = _nowPlayingMoviesResults

    private val _movieTrailer = MutableLiveData<List<MovieTrailerResults>>()
    val movieTrailerResults: LiveData<List<MovieTrailerResults>> = _movieTrailer

    private val _movieTrailerKey = MutableLiveData<String?>()
    val movieTrailerKey: LiveData<String?> = _movieTrailerKey

    fun fetchMovie(movieId: Int , apiKey :String) {
        val call = repository.getMovieById(movieId, apiKey )
        call.enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.isSuccessful) {
                    _movie.value = response.body()
                } else {
                    _movie.value = null  // Handle API error
                }
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                _movie.value = null  // Handle network failure
            }
        })
    }
    fun fetchPopularMovies(apiKey: String){
        val call = repository.getPopularMovie(apiKey)
        call.enqueue(object  : Callback<MoviePopularResponse>{
            override fun onResponse(call: Call<MoviePopularResponse>, response: Response<MoviePopularResponse>){
                if (response.isSuccessful){
                    _popularResults.value = response.body()?.results ?: emptyList()
                } else{
                    _popularResults.value = emptyList()
                }
            }

            override fun onFailure(call: Call<MoviePopularResponse>, t: Throwable) {
                _popularResults.value = emptyList()
            }
        } )
    }
    fun fetchBestMovies(apiKey: String){
        val call = repository.getBestMovies(apiKey)
        call.enqueue(object  : Callback<MoviePopularResponse>{
            override fun onResponse(call: Call<MoviePopularResponse>, response: Response<MoviePopularResponse>){
                if (response.isSuccessful){
                    _bestMoviesResults.value = response.body()?.results ?: emptyList()
                } else{
                    _bestMoviesResults.value = emptyList()
                }
            }

            override fun onFailure(call: Call<MoviePopularResponse>, t: Throwable) {
                _bestMoviesResults.value = emptyList()
            }
        } )
    }
    fun fetchNowPlayingMovies(apiKey: String){
        val call = repository.getNowPlayingMovies(apiKey)
        call.enqueue(object  : Callback<MoviePopularResponse>{
            override fun onResponse(call: Call<MoviePopularResponse>, response: Response<MoviePopularResponse>){
                if (response.isSuccessful){
                    _nowPlayingMoviesResults.value = response.body()?.results ?: emptyList()
                } else{
                    _nowPlayingMoviesResults.value = emptyList()
                }
            }

            override fun onFailure(call: Call<MoviePopularResponse>, t: Throwable) {
                _nowPlayingMoviesResults.value = emptyList()
            }
        } )
    }
    fun fetchMovieTrailer(movieId: Int, apiKey: String) {
        val call = repository.getMovieVideos(movieId, apiKey)
        call.enqueue(object : Callback<MovieTrailerResponse> {
            override fun onResponse(
                call: Call<MovieTrailerResponse>,
                response: Response<MovieTrailerResponse>
            ) {
                if (response.isSuccessful) {
                    // Filter to find the first YouTube trailer
                    val trailer = response.body()?.results?.firstOrNull { it.trailerSite == "YouTube" && it.trailerType == "Trailer" }
                    val trailerKey = trailer?.trailerKey

                    // Update LiveData with the trailer key
                    _movieTrailerKey.value = trailerKey
                } else {
                    _movieTrailerKey.value = null
                }
            }

            override fun onFailure(call: Call<MovieTrailerResponse>, t: Throwable) {
                _movieTrailerKey.value = null
            }
        })
    }

}

