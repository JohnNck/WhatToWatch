import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whattowatch.R
import com.example.whattowatch.response.MoviePopularResults

class MovieAdapter(
    private val movieList: List<MoviePopularResults>,
    private val onItemClick: (MoviePopularResults) -> Unit // Add click listener parameter
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.movieTitle.text = movie.title

        // Load movie poster using Glide
        val posterUrl = "https://image.tmdb.org/t/p/w500" + movie.posterPath
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .error(R.drawable.error_image) // Show error image if load fails
            .into(holder.moviePoster)

        // Set click listener on item view
        holder.itemView.setOnClickListener {
            onItemClick(movie) // Invoke the click listener with the clicked movie item
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
    }
}