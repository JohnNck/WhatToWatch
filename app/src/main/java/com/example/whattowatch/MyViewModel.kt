
package com.example.whattowatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whattowatch.domain.repository.MyRepository
import com.example.whattowatch.response.Genres
import com.example.whattowatch.response.GenresResponse
import com.example.whattowatch.response.MovieModel
import com.example.whattowatch.response.MovieResponse
import com.example.whattowatch.response.MovieResults
import com.example.whattowatch.response.MovieTrailerResponse
import com.example.whattowatch.response.SeriesResponse
import com.example.whattowatch.response.SeriesResults
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

    fun fetchMovie(movieId: Int , apiKey :String) {
        val call = repository.getMovieById(movieId, apiKey )
        call.enqueue(object : Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                if (response.isSuccessful) {
                    _movie.value = response.body()
                } else {
                    _movie.value = null
                }
            }
            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                _movie.value = null
            }
        })
    }

    private val _popularResults = MutableLiveData<List<MovieResults>>()
    val popularResults: LiveData<List<MovieResults>> = _popularResults

    private val _popularMoviesTotalPages = MutableLiveData<Int>()
    val popularMoviesTotalPages: LiveData<Int> = _popularMoviesTotalPages

    fun fetchPopularMovies(apiKey: String,page : Int){
        val call = repository.getPopularMovie(apiKey,page)
        call.enqueue(object  : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>){
                if (response.isSuccessful){
                    _popularResults.value = response.body()?.results ?: emptyList()
                    _popularMoviesTotalPages.value = response.body()?.totalPages ?: 1

                } else{
                    _popularResults.value = emptyList()
                    _popularMoviesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _popularResults.value = emptyList()
                _popularMoviesTotalPages.value = 1
            }
        } )
    }

    private val _bestMoviesResults = MutableLiveData<List<MovieResults>>()
    val bestMoviesResults: LiveData<List<MovieResults>> = _bestMoviesResults

    private val _bestMoviesTotalPages = MutableLiveData<Int>()
    val bestMoviesTotalPages: LiveData<Int> = _bestMoviesTotalPages

    fun fetchBestMovies(apiKey: String,page : Int){
        val call = repository.getBestMovies(apiKey,page)
        call.enqueue(object  : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>){
                if (response.isSuccessful){
                    _bestMoviesResults.value = response.body()?.results ?: emptyList()
                    _bestMoviesTotalPages.value = response.body()?.totalPages ?: 1

                } else{
                    _bestMoviesResults.value = emptyList()
                    _bestMoviesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _bestMoviesResults.value = emptyList()
                _bestMoviesTotalPages.value = 1
            }
        } )
    }

    private val _upcomingMovies = MutableLiveData<List<MovieResults>>()
    val upcomingMovies: LiveData<List<MovieResults>> = _upcomingMovies

    private val _upcomingMoviesTotalPages = MutableLiveData<Int>()
    val upcomingMoviesTotalPages: LiveData<Int> = _upcomingMoviesTotalPages

    fun fetchUpcomingMovies(apiKey: String,page : Int){
        val call = repository.getUpcomingMovies(apiKey,page)
        call.enqueue(object  : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>){
                if (response.isSuccessful){
                    _upcomingMovies.value = response.body()?.results ?: emptyList()
                    _upcomingMoviesTotalPages.value = response.body()?.totalPages ?: 1

                } else{
                    _upcomingMovies.value = emptyList()
                    _upcomingMoviesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _upcomingMovies.value = emptyList()
                _upcomingMoviesTotalPages.value = 1
            }
        } )
    }

    private val _nowPlayingMoviesResults = MutableLiveData<List<MovieResults>>()
    val nowPlayingMoviesResults: LiveData<List<MovieResults>> = _nowPlayingMoviesResults

    private val _nowPlayingMoviesTotalPages = MutableLiveData<Int>()
    val nowPlayingMoviesTotalPages: LiveData<Int> = _nowPlayingMoviesTotalPages

    fun fetchNowPlayingMovies(apiKey: String,page : Int){
        val call = repository.getNowPlayingMovies(apiKey,page)
        call.enqueue(object  : Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>){
                if (response.isSuccessful){
                    _nowPlayingMoviesResults.value = response.body()?.results ?: emptyList()
                    _nowPlayingMoviesTotalPages.value = response.body()?.totalPages ?: 1

                } else{
                    _nowPlayingMoviesResults.value = emptyList()
                    _nowPlayingMoviesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _nowPlayingMoviesResults.value = emptyList()
                _nowPlayingMoviesTotalPages.value = 1
            }
        } )
    }

    private val _nowPlayingSeries = MutableLiveData<List<SeriesResults>>()
    val nowPlayingSeries: LiveData<List<SeriesResults>> = _nowPlayingSeries

    private val _nowPlayingSeriesTotalPages = MutableLiveData<Int>()
    val nowPlayingSeriesTotalPages: LiveData<Int> = _nowPlayingSeriesTotalPages

    fun fetchNowPlayingSeries(apiKey: String,page : Int){
        val call = repository.getNowPlayingSeries(apiKey,page)
        call.enqueue(object  : Callback<SeriesResponse>{
            override fun onResponse(call: Call<SeriesResponse>, response: Response<SeriesResponse>){
                if (response.isSuccessful){
                    _nowPlayingSeries.value = response.body()?.results ?: emptyList()
                    _nowPlayingSeriesTotalPages.value = response.body()?.totalPages ?: 1
                } else{
                    _nowPlayingSeries.value = emptyList()
                    _nowPlayingSeriesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                _nowPlayingSeries.value = emptyList()
                _nowPlayingSeriesTotalPages.value = 1
            }
        } )
    }

    private val _topRatedSeries = MutableLiveData<List<SeriesResults>>()
    val topRatedSeries: LiveData<List<SeriesResults>> = _topRatedSeries

    private val _topRatedSeriesTotalPages = MutableLiveData<Int>()
    val topRatedSeriesTotalPages: LiveData<Int> = _topRatedSeriesTotalPages

    fun fetchTopRatedSeries(apiKey: String,page : Int){
        val call = repository.getTopRatedSeries(apiKey,page)
        call.enqueue(object  : Callback<SeriesResponse>{
            override fun onResponse(call: Call<SeriesResponse>, response: Response<SeriesResponse>){
                if (response.isSuccessful){
                    _topRatedSeries.value = response.body()?.results ?: emptyList()
                    _topRatedSeriesTotalPages.value = response.body()?.totalPages ?: 1

                } else{
                    _topRatedSeries.value = emptyList()
                    _topRatedSeriesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                _topRatedSeries.value = emptyList()
                _topRatedSeriesTotalPages.value = 1
            }
        } )
    }

    private val _popularSeries = MutableLiveData<List<SeriesResults>>()
    val popularSeries: LiveData<List<SeriesResults>> = _popularSeries

    private val _popularSeriesTotalPages = MutableLiveData<Int>()
    val popularSeriesTotalPages: LiveData<Int> = _popularSeriesTotalPages

    fun fetchPopularSeries(apiKey: String,page : Int){
        val call = repository.getPopularSeries(apiKey,page)
        call.enqueue(object  : Callback<SeriesResponse>{
            override fun onResponse(call: Call<SeriesResponse>, response: Response<SeriesResponse>){
                if (response.isSuccessful){
                    _popularSeries.value = response.body()?.results ?: emptyList()
                    _popularSeriesTotalPages.value = response.body()?.totalPages ?: 1

                } else{
                    _popularSeries.value = emptyList()
                    _popularSeriesTotalPages.value = 1
                }
            }
            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                _popularSeries.value = emptyList()
                _popularSeriesTotalPages.value = 1
            }
        } )
    }

    private val _movieTrailerKey = MutableLiveData<String?>()
    val movieTrailerKey: LiveData<String?> = _movieTrailerKey

    fun fetchMovieTrailer(movieId: Int, apiKey: String) {
        val call = repository.getMovieVideos(movieId, apiKey)
        call.enqueue(object : Callback<MovieTrailerResponse> {
            override fun onResponse(
                call: Call<MovieTrailerResponse>,
                response: Response<MovieTrailerResponse>
            ) {
                if (response.isSuccessful) {
                    val trailer = response.body()?.results?.firstOrNull { it.trailerSite == "YouTube" && it.trailerType == "Trailer" }
                    val trailerKey = trailer?.trailerKey
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

    private val _seriesTrailerKey = MutableLiveData<String?>()
    val seriesTrailerKey: LiveData<String?> = _seriesTrailerKey

    fun fetchSeriesTrailer(seriesId: Int, apiKey: String) {
        val call = repository.getSeriesVideos(seriesId, apiKey)
        call.enqueue(object : Callback<MovieTrailerResponse> {
            override fun onResponse(
                call: Call<MovieTrailerResponse>,
                response: Response<MovieTrailerResponse>
            ) {
                if (response.isSuccessful) {
                    val trailer = response.body()?.results?.firstOrNull { it.trailerSite == "YouTube" && (it.trailerType == "Trailer" || it.trailerType == "Opening Credits")}
                    val trailerKey = trailer?.trailerKey
                    _seriesTrailerKey.value = trailerKey
                } else {
                    _seriesTrailerKey.value = null
                }
            }
            override fun onFailure(call: Call<MovieTrailerResponse>, t: Throwable) {
                _movieTrailerKey.value = null
            }
        })
    }

    private val _movieGenres = MutableLiveData<List<Genres>>()
    val movieGenres: LiveData<List<Genres>> get() = _movieGenres

    fun fetchMovieGenres(apiKey: String){
        val call = repository.getMoviesGenres(apiKey)
        call.enqueue(object  : Callback<GenresResponse>{
            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>){
                if (response.isSuccessful){
                    _movieGenres.value = response.body()?.genres ?: emptyList()
                } else{
                    _movieGenres.value = emptyList()
                }
            }
            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                _movieGenres.value = emptyList()
            }
        } )
    }
    private val _seriesGenres = MutableLiveData<List<Genres>>()
    val seriesGenres: LiveData<List<Genres>> get() = _seriesGenres
    fun fetchSeriesGenres(apiKey: String) {
        repository.getSeriesGenres(apiKey).enqueue(object : Callback<GenresResponse> {
            override fun onResponse(call: Call<GenresResponse>, response: Response<GenresResponse>) {
                if (response.isSuccessful) {
                    _seriesGenres.value = response.body()?.genres
                }
            }
            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                _seriesGenres.value = emptyList()
            }
        })
    }

    private val _movieSearchResults = MutableLiveData<List<MovieResults>?>()
    val movieSearchResults: MutableLiveData<List<MovieResults>?> get() = _movieSearchResults

    fun searchMovies(apiKey: String, query: String) {
        repository.getMovieSearch(apiKey, query).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _movieSearchResults.value = results
                }
            }
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _movieSearchResults.value = emptyList()
            }
        })
    }

    private val _seriesSearchResults = MutableLiveData<List<SeriesResults>?>()
    val seriesSearchResults: MutableLiveData<List<SeriesResults>?> get() = _seriesSearchResults

    fun searchSeries(apiKey: String, query: String) {
        repository.getSeriesSearch(apiKey, query).enqueue(object : Callback<SeriesResponse> {
            override fun onResponse(call: Call<SeriesResponse>, response: Response<SeriesResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _seriesSearchResults.value = results
                }
            }
            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                _seriesSearchResults.value = emptyList()
            }
        })
    }

    private val _movieDiscoverResults = MutableLiveData<List<MovieResults>>()
    val movieDiscoverResults: LiveData<List<MovieResults>> = _movieDiscoverResults

    private val _seriesDiscoverResults = MutableLiveData<List<SeriesResults>>()
    val seriesDiscoverResults: LiveData<List<SeriesResults>> = _seriesDiscoverResults

    private val _discoverSeriesTotalPages = MutableLiveData<Int>()
    val discoverSeriesTotalPages: LiveData<Int> = _discoverSeriesTotalPages


    private val _discoverMoviesTotalPages = MutableLiveData<Int>()
    val discoverMoviesTotalPages: LiveData<Int> = _discoverMoviesTotalPages


    fun discoverMoviesByGenres(apiKey: String,page : Int, genreIds: List<Int>) {
        val genreQuery = genreIds.joinToString(",")
        repository.getDiscoverMovies(apiKey, page,genreQuery).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _movieDiscoverResults.value = results ?: emptyList()
                    _discoverMoviesTotalPages.value = response.body()?.totalPages ?: 1

                } else {
                    _movieDiscoverResults.value = emptyList()
                    _discoverMoviesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                _movieDiscoverResults.value = emptyList()
                _discoverMoviesTotalPages.value = 1
            }
        })
    }


    fun discoverSeriesByGenres(apiKey: String,page : Int, genreIds: List<Int>) {
        val genreQuery = genreIds.joinToString(",")
        repository.getDiscoverSeries(apiKey,page, genreQuery).enqueue(object : Callback<SeriesResponse> {
            override fun onResponse(call: Call<SeriesResponse>, response: Response<SeriesResponse>) {
                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _seriesDiscoverResults.value = results ?: emptyList()
                    _discoverSeriesTotalPages.value = response.body()?.totalPages ?: 1

                } else {
                    _seriesDiscoverResults.value = emptyList()
                    _discoverSeriesTotalPages.value = 1
                }
            }

            override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                _seriesDiscoverResults.value = emptyList()
                _discoverSeriesTotalPages.value = 1
            }
        })
    }

}

