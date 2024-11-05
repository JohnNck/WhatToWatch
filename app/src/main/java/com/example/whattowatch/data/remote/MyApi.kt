package com.example.whattowatch.data.remote

import com.example.whattowatch.data.remote.EndPoints.NOW_PLAYING_SERIES
import com.example.whattowatch.data.remote.EndPoints.BEST_MOVIE
import com.example.whattowatch.data.remote.EndPoints.DISCOVER_MOVIES
import com.example.whattowatch.data.remote.EndPoints.DISCOVER_SERIES
import com.example.whattowatch.data.remote.EndPoints.MOVIES_GENRES
import com.example.whattowatch.data.remote.EndPoints.MOVIE_BY_ID
import com.example.whattowatch.data.remote.EndPoints.MOVIE_SEARCH
import com.example.whattowatch.data.remote.EndPoints.MOVIE_VIDEOS
import com.example.whattowatch.data.remote.EndPoints.NOW_PLAYING
import com.example.whattowatch.data.remote.EndPoints.POPULAR_MOVIE
import com.example.whattowatch.data.remote.EndPoints.POPULAR_SERIES
import com.example.whattowatch.data.remote.EndPoints.SERIES_GENRES
import com.example.whattowatch.data.remote.EndPoints.SERIES_SEARCH
import com.example.whattowatch.data.remote.EndPoints.SERIES_VIDEOS
import com.example.whattowatch.data.remote.EndPoints.TOP_RATED_SERIES
import com.example.whattowatch.data.remote.EndPoints.UPCOMING_MOVIES
import com.example.whattowatch.response.GenresResponse
import com.example.whattowatch.response.MovieModel
import com.example.whattowatch.response.MovieResponse
import com.example.whattowatch.response.MovieTrailerResponse
import com.example.whattowatch.response.SeriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object EndPoints {
    const val MOVIE_BY_ID = "3/movie/{movie_id}"
    const val POPULAR_MOVIE = "3/movie/popular"
    const val MOVIE_SEARCH = "3/search/movie"
    const val SERIES_SEARCH = "3/search/tv"
    const val BEST_MOVIE = "3/movie/top_rated"
    const val NOW_PLAYING = "3/movie/now_playing"
    const val MOVIE_VIDEOS = "3/movie/{movie_id}/videos"
    const val UPCOMING_MOVIES = "3/movie/upcoming"
    const val NOW_PLAYING_SERIES = "3/tv/airing_today"
    const val POPULAR_SERIES = "3/tv/popular"
    const val TOP_RATED_SERIES = "3/tv/top_rated"
    const val SERIES_VIDEOS = "3/tv/{series_id}/videos"
    const val MOVIES_GENRES = "3/genre/movie/list"
    const val SERIES_GENRES = "3/genre/tv/list"
    const val DISCOVER_MOVIES = "3/discover/movie"
    const val DISCOVER_SERIES = "3/discover/tv"
}

interface MyApi {

    @GET(MOVIE_BY_ID)
    fun getMovieById(@Path("movie_id") movieId: Int,@Query("api_key") apiKey : String) : Call<MovieModel>

    @GET(POPULAR_MOVIE)
    fun getPopularMovie(@Query("api_key") apiKey: String, @Query("page") page : Int ) : Call<MovieResponse>

    @GET(MOVIE_SEARCH)
    fun getMovieSearch(@Query("api_key") apiKey: String,@Query("query") query : String) : Call<MovieResponse>

    @GET(BEST_MOVIE)
    fun getBestMovies(@Query("api_key") apiKey: String, @Query("page") page : Int  ) : Call<MovieResponse>

    @GET(NOW_PLAYING)
    fun getNowPlayingMovies(@Query("api_key") apiKey: String, @Query("page") page : Int  ) : Call<MovieResponse>

    @GET(MOVIE_VIDEOS)
    fun getMovieVideos(@Path("movie_id") movieId: Int,@Query("api_key") apiKey : String) : Call<MovieTrailerResponse>

    @GET(UPCOMING_MOVIES)
    fun getUpcomingMovies(@Query("api_key") apiKey : String, @Query("page") page : Int ) : Call<MovieResponse>

    @GET(NOW_PLAYING_SERIES)
    fun getNowPlayingSeries(@Query("api_key") apiKey : String, @Query("page") page : Int ) : Call<SeriesResponse>

    @GET(POPULAR_SERIES)
    fun getPopularSeries(@Query("api_key") apiKey : String, @Query("page") page : Int ) : Call<SeriesResponse>

    @GET(TOP_RATED_SERIES)
    fun getTopRatedSeries(@Query("api_key") apiKey : String, @Query("page") page : Int ) : Call<SeriesResponse>

    @GET(SERIES_VIDEOS)
    fun getSeriesVideos(@Path("series_id") movieId: Int,@Query("api_key") apiKey : String) : Call<MovieTrailerResponse>

    @GET(SERIES_GENRES)
    fun getSeriesGenres(@Query("api_key") apiKey : String) : Call<GenresResponse>

    @GET(MOVIES_GENRES)
    fun getMoviesGenres(@Query("api_key") apiKey : String) : Call<GenresResponse>

    @GET(SERIES_SEARCH)
    fun getSeriesSearch(@Query("api_key") apiKey : String, @Query("query") query : String) : Call<SeriesResponse>

    @GET(DISCOVER_MOVIES)
    fun getDiscoverMovies(@Query("api_key") apiKey : String, @Query("page") page : Int, @Query("with_genres") genreIds: String  ) : Call<MovieResponse>

    @GET(DISCOVER_SERIES)
    fun getDiscoverSeries(@Query("api_key") apiKey : String, @Query("page") page : Int, @Query("with_genres") genreIds: String ) : Call<SeriesResponse>

}