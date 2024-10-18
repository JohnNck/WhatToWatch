package com.example.whattowatch.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieModel(
    @SerializedName("title") val title :String,
    @SerializedName("poster_path") val posterPath :String,
    @SerializedName("release_date") val releaseDate :String,
    @SerializedName("movieId") val movieId : Int,
    @SerializedName("vote_average") val voteAverage : Float,
    @SerializedName("overview") val movieOverview : String,
 ): Serializable

data class MoviePopularResponse(
    @SerializedName("results") val results: List<MoviePopularResults>
): Serializable

data class MoviePopularResults(
    @SerializedName("total_results") val totalResults :String,
    @SerializedName("title") val title :String,
    @SerializedName("id") val movieId : Int,
    @SerializedName("overview") val overView :String,
    @SerializedName("poster_path") val posterPath :String,
    @SerializedName("release_date") val releaseDate :String,
    @SerializedName("backdrop_path") val backdropPath :String,
    @SerializedName("vote_average") val voteAverage: Float

): Serializable
data class MovieBestResults(
    @SerializedName("total_results") val totalResults :String,
    @SerializedName("title") val title :String,
    @SerializedName("poster_path") val posterPath :String,
    @SerializedName("release_date") val releaseDate :String,
    @SerializedName("backdrop_path") val backdropPath :String,
    @SerializedName("vote_average") val voteAverage: Float
    ): Serializable

data class MovieTrailerResponse(
    @SerializedName("results") val results: List<MovieTrailerResults>
): Serializable

data class MovieTrailerResults(
    @SerializedName("key") val trailerKey :String,
    @SerializedName("name") val trailerName :String,
    @SerializedName("site") val trailerSite :String,
    @SerializedName("type") val trailerType :String,
): Serializable