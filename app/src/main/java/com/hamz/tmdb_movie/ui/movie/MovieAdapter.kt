package com.hamz.tmdb_movie.ui.movie

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamz.tmdb_movie.R
import com.hamz.tmdb_movie.data.model.Movie
import com.hamz.tmdb_movie.util.Constant
import com.hamz.tmdb_movie.util.Tmdb
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movies: ArrayList<Movie?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            MovieViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
            )
        } else {
            LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_loading, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val holder = holder as MovieViewHolder
            holder.bind(movies[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (movies[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = movies.size

    fun addLoadingView() {
        Handler().post {
            movies.add(null)
            notifyItemInserted(movies.size - 1)
        }
    }

    fun removeLoadingView() {
        if (movies.size != 0) {
            movies.removeAt(movies.size - 1)
            notifyItemRemoved(movies.size)
        }
    }

    fun addData(movies: ArrayList<Movie?>) {
        this.movies.addAll(movies)
        notifyDataSetChanged()

        Log.e("adapter", "total movies size: ${this.movies.size}")
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie?) {
            with(itemView) {
                text_title.text = movie?.title ?: ""
                text_rate.text = movie?.voteAverage.toString() ?: "0"

                val imageUrl = "${Tmdb.IMAGE_URL}${movie?.posterPath}"
                Glide
                    .with(context)
                    .load(imageUrl)
                    .into(image_poster)

                setOnClickListener {
                    if (movie != null)
                        listener.onMovieClick(movie)
                }
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onMovieClick(movie: Movie)
    }
}