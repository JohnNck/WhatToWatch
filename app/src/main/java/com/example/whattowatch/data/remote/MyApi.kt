package com.example.whattowatch.data.remote

import com.example.whattowatch.data.remote.EndPoints.BEST_MOVIE
import com.example.whattowatch.data.remote.EndPoints.MOVIE_BY_ID
import com.example.whattowatch.data.remote.EndPoints.MOVIE_SEARCH
import com.example.whattowatch.data.remote.EndPoints.MOVIE_VIDEOS
import com.example.whattowatch.data.remote.EndPoints.NOW_PLAYING
import com.example.whattowatch.data.remote.EndPoints.POPULAR_MOVIE
import com.example.whattowatch.response.MovieModel
import com.example.whattowatch.response.MoviePopularResponse
import com.example.whattowatch.response.MovieTrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object EndPoints {
    const val MOVIE_BY_ID = "3/movie/{movie_id}"
    const val POPULAR_MOVIE = "3/movie/popular"
    const val MOVIE_SEARCH = "3/movie/search/movie"
    const val BEST_MOVIE = "3/movie/top_rated"
    const val NOW_PLAYING = "3/movie/now_playing"
    const val MOVIE_VIDEOS = "3/movie/{movie_id}/videos"
}

interface MyApi {

    @GET(MOVIE_BY_ID)
    fun getMovieById(@Path("movie_id") movieId: Int,@Query("api_key") apiKey : String) : Call<MovieModel>

    @GET(POPULAR_MOVIE)
    fun getPopularMovie(@Query("api_key") apiKey: String ) : Call<MoviePopularResponse>

    @GET(MOVIE_SEARCH)
    fun getMovieSearch(@Query("api_key") apiKey: String,@Query("query") query : String) : Call<MoviePopularResponse>

    @GET(BEST_MOVIE)
    fun getBestMovies(@Query("api_key") apiKey: String ) : Call<MoviePopularResponse>

    @GET(NOW_PLAYING)
    fun getNowPlayingMovies(@Query("api_key") apiKey: String ) : Call<MoviePopularResponse>

    @GET(MOVIE_VIDEOS)
    fun getMovieVideos(@Path("movie_id") movieId: Int,@Query("api_key") apiKey : String) : Call<MovieTrailerResponse>

}