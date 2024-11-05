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

class SeriesAdapter(
    private val seriesList: List<SeriesResults>,
    private val onItemClick: (SeriesResults) -> Unit
) : RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.series_item, parent, false)
        return SeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = seriesList[position]
        holder.seriesTitle.text = series.name
        val posterUrl = "https://image.tmdb.org/t/p/w500${series.posterPath}"
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .error(R.drawable.error_image)
            .into(holder.seriesPoster)
        holder.itemView.setOnClickListener {
            onItemClick(series)
        }
    }

    override fun getItemCount(): Int {
        return seriesList.size
    }

    class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seriesTitle: TextView = itemView.findViewById(R.id.SeriesTitle)
        val seriesPoster: ImageView = itemView.findViewById(R.id.seriesPoster)
    }
}
