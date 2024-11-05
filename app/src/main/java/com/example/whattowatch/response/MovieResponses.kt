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

data class MovieResponse(
    @SerializedName("results") val results: List<MovieResults>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
): Serializable

data class MovieResults(
    @SerializedName("total_results") val totalResults :String,
    @SerializedName("title") val title :String,
    @SerializedName("id") val movieId : Int,
    @SerializedName("overview") val overView :String,
    @SerializedName("poster_path") val posterPath :String,
    @SerializedName("release_date") val releaseDate :String,
    @SerializedName("backdrop_path") val backdropPath :String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("genre_ids")  val genreIds: List<Int>,

): Serializable
data class SeriesResults(
    @SerializedName("id") val seriesId :Int,
    @SerializedName("total_results") val totalResults :String,
    @SerializedName("name") val name :String,
    @SerializedName("poster_path") val posterPath :String,
    @SerializedName("overview") val overView :String,
    @SerializedName("release_date") val releaseDate :String,
    @SerializedName("backdrop_path") val backdropPath :String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("genre_ids")  val genreIds: List<Int>,
    ): Serializable

data class SeriesResponse(
    @SerializedName("results") val results: List<SeriesResults>,
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
): Serializable

data class MovieTrailerResponse(
    @SerializedName("results") val results: List<MovieTrailerResults>,
): Serializable

data class MovieTrailerResults(
    @SerializedName("key") val trailerKey :String,
    @SerializedName("name") val trailerName :String,
    @SerializedName("site") val trailerSite :String,
    @SerializedName("type") val trailerType :String,
): Serializable

data class GenresResponse(
    @SerializedName("genres") val genres :List<Genres>,
): Serializable

data class Genres(
    @SerializedName("id") val id :Int,
    @SerializedName("name") val name :String,

):Serializable
