package com.example.whattowatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowatch.R
import com.example.whattowatch.response.Genres

class GenreAdapter(
    private var genreList: List<Genres>,
    private val onGenreClick: (Genres) -> Unit
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private val selectedGenres = mutableSetOf<Genres>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genreList[position]
        holder.genreName.text = genre.name
        val isSelected = selectedGenres.contains(genre)
        val textColor = if (isSelected) R.color.yellow else R.color.white
        holder.genreName.setTextColor(ContextCompat.getColor(holder.itemView.context, textColor))
        holder.itemView.setOnClickListener {
            if (isSelected) {
                selectedGenres.remove(genre)
            } else {
                selectedGenres.add(genre)
            }
            onGenreClick(genre)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreName: TextView = itemView.findViewById(R.id.genres)
    }

    fun getSelectedGenres(): Set<Genres> {
        return selectedGenres
    }
    fun updateGenres(newGenres: List<Genres>) {
        genreList = newGenres
        notifyDataSetChanged()
    }
}
