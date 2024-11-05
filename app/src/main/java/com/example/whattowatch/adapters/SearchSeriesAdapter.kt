package com.example.whattowatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whattowatch.R
import com.example.whattowatch.response.SeriesResults

class SearchSeriesAdapter(
    private val onItemClick: (SeriesResults) -> Unit
) : RecyclerView.Adapter<SearchSeriesAdapter.SearchSeriesViewHolder>() {

    private val searchSeriesList = mutableListOf<SeriesResults>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSeriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return SearchSeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchSeriesViewHolder, position: Int) {
        val series = searchSeriesList[position]
        holder.seriesTitle.text = series.name

        val posterUrl = "https://image.tmdb.org/t/p/w500${series.posterPath}"
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .error(R.drawable.image_not_supported)
            .into(holder.seriesPoster)

        holder.itemView.setOnClickListener {
            onItemClick(series)
        }
    }

    override fun getItemCount(): Int = searchSeriesList.size

    class SearchSeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seriesTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val seriesPoster: ImageView = itemView.findViewById(R.id.moviePoster)
    }

    fun updateSeriesResults(newSeries: List<SeriesResults>) {
        searchSeriesList.clear()
        searchSeriesList.addAll(newSeries)
        notifyDataSetChanged()
    }
}
