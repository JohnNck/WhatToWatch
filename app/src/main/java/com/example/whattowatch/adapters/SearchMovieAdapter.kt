package com.example.whattowatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whattowatch.R
import com.example.whattowatch.response.MovieResults

class SearchMovieAdapter(
    private val onItemClick: (MovieResults) -> Unit
) : RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder>() {
    private val searchMovieList = mutableListOf<MovieResults>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return SearchMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val movie = searchMovieList[position]
        holder.movieTitle.text = movie.title
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .error(R.drawable.image_not_supported)
            .into(holder.moviePoster)
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    override fun getItemCount(): Int = searchMovieList.size

    class SearchMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
    }
    fun updateSearchResults(newMovies: List<MovieResults>) {
        searchMovieList.clear()
        searchMovieList.addAll(newMovies)
        notifyDataSetChanged()
    }
}
