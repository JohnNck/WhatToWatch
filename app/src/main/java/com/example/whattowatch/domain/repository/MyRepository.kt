// MyRepository.kt
package com.example.whattowatch.domain.repository

import com.example.whattowatch.data.remote.MyApi
import com.example.whattowatch.response.MovieModel
import com.example.whattowatch.response.MoviePopularResponse
import com.example.whattowatch.response.MovieTrailerResponse
import retrofit2.Call
import javax.inject.Inject


interface MyRepository{
    fun getMovieById(movieId: Int,apiKey :String): Call<MovieModel>
    fun getPopularMovie(apiKey: String ) : Call<MoviePopularResponse>
    fun getMovieSearch(apiKey: String , query : String) : Call<MoviePopularResponse>
    fun getBestMovies(apiKey: String ) : Call<MoviePopularResponse>
    fun getNowPlayingMovies(apiKey: String ) : Call<MoviePopularResponse>
    fun getMovieVideos(movieId: Int, apiKey: String ) : Call<MovieTrailerResponse>
}

class MyRepositoryImpl @Inject constructor(
    private val api: MyApi
) : MyRepository {
    override fun getMovieById(movieId: Int, apiKey: String): Call<MovieModel> {
        return api.getMovieById(movieId, apiKey)
    }

    override fun getPopularMovie(apiKey: String): Call<MoviePopularResponse> {
        return api.getPopularMovie(apiKey)
    }

    override fun getMovieSearch(apiKey: String, query: String): Call<MoviePopularResponse> {
        return api.getMovieSearch(apiKey, query)
    }

    override fun getBestMovies(apiKey: String): Call<MoviePopularResponse> {
        return api.getBestMovies(apiKey)
    }

    override fun getNowPlayingMovies(apiKey: String): Call<MoviePopularResponse> {
        return api.getNowPlayingMovies(apiKey)
    }

    override fun getMovieVideos(movieId: Int, apiKey: String): Call<MovieTrailerResponse> {
        return api.getMovieVideos(movieId, apiKey)
    }
}