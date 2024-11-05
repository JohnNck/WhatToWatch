// MyRepository.kt
package com.example.whattowatch.domain.repository

import com.example.whattowatch.data.remote.MyApi
import com.example.whattowatch.response.GenresResponse
import com.example.whattowatch.response.MovieModel
import com.example.whattowatch.response.MovieResponse
import com.example.whattowatch.response.MovieTrailerResponse
import com.example.whattowatch.response.SeriesResponse
import retrofit2.Call
import javax.inject.Inject


interface MyRepository{
    fun getMovieById(movieId: Int,apiKey :String): Call<MovieModel>
    fun getPopularMovie(apiKey: String,page : Int ) : Call<MovieResponse>
    fun getMovieSearch(apiKey: String , query : String) : Call<MovieResponse>
    fun getSeriesSearch(apiKey: String , query : String) : Call<SeriesResponse>
    fun getBestMovies(apiKey: String,page : Int ) : Call<MovieResponse>
    fun getNowPlayingMovies(apiKey: String,page : Int ) : Call<MovieResponse>
    fun getMovieVideos(movieId: Int, apiKey: String ) : Call<MovieTrailerResponse>
    fun getUpcomingMovies(apiKey: String,page : Int ) : Call<MovieResponse>
    fun getTopRatedSeries(apiKey: String,page : Int ) : Call<SeriesResponse>
    fun getNowPlayingSeries(apiKey: String,page : Int ) : Call<SeriesResponse>
    fun getPopularSeries(apiKey: String,page : Int ) : Call<SeriesResponse>
    fun getSeriesVideos(seriesId: Int, apiKey: String ) : Call<MovieTrailerResponse>
    fun getMoviesGenres(apiKey: String) : Call<GenresResponse>
    fun getSeriesGenres(apiKey: String) : Call<GenresResponse>
    fun getDiscoverMovies(apiKey: String,page : Int, genreIds: String) : Call<MovieResponse>
        fun getDiscoverSeries(apiKey: String,page : Int, genreIds: String) : Call<SeriesResponse>
}

class MyRepositoryImpl @Inject constructor(
    private val api: MyApi
) : MyRepository {
    override fun getMovieById(movieId: Int, apiKey: String): Call<MovieModel> {
        return api.getMovieById(movieId, apiKey)
    }

    override fun getPopularMovie(apiKey: String, page : Int): Call<MovieResponse> {
        return api.getPopularMovie(apiKey ,page)
    }

    override fun getMovieSearch(apiKey: String, query: String): Call<MovieResponse> {
        return api.getMovieSearch(apiKey, query)
    }

    override fun getBestMovies(apiKey: String,page : Int): Call<MovieResponse> {
        return api.getBestMovies(apiKey,page)
    }

    override fun getNowPlayingMovies(apiKey: String,page : Int): Call<MovieResponse> {
        return api.getNowPlayingMovies(apiKey,page)
    }

    override fun getMovieVideos(movieId: Int, apiKey: String): Call<MovieTrailerResponse> {
        return api.getMovieVideos(movieId, apiKey)
    }

    override fun getUpcomingMovies(apiKey: String,page : Int): Call<MovieResponse> {
        return api.getUpcomingMovies(apiKey,page)
    }

    override fun getTopRatedSeries(apiKey: String,page : Int): Call<SeriesResponse> {
        return api.getTopRatedSeries(apiKey,page)
    }

    override fun getNowPlayingSeries(apiKey: String,page : Int): Call<SeriesResponse> {
        return api.getNowPlayingSeries(apiKey,page)
    }

    override fun getPopularSeries(apiKey: String,page : Int): Call<SeriesResponse> {
        return api.getPopularSeries(apiKey,page)
    }

    override fun getSeriesVideos(seriesId: Int, apiKey: String): Call<MovieTrailerResponse> {
        return api.getSeriesVideos(seriesId, apiKey)
    }

    override fun getMoviesGenres(apiKey: String): Call<GenresResponse> {
        return api.getMoviesGenres(apiKey)
    }

    override fun getSeriesGenres(apiKey: String): Call<GenresResponse> {
       return api.getSeriesGenres(apiKey)
    }

    override fun getSeriesSearch(apiKey: String, query: String): Call<SeriesResponse> {
        return api.getSeriesSearch(apiKey, query)
    }

    override fun getDiscoverMovies(apiKey: String,page : Int, genreIds: String): Call<MovieResponse> {
        return api.getDiscoverMovies(apiKey,page, genreIds)
    }

    override fun getDiscoverSeries(apiKey: String,page : Int,genreIds: String): Call<SeriesResponse> {
       return api.getDiscoverSeries(apiKey, page, genreIds)
    }
}