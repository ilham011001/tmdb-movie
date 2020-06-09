package com.hamz.tmdb_movie.ui.genre

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamz.tmdb_movie.R
import com.hamz.tmdb_movie.data.model.Genre
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreAdapter(private val listener: Listener) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    private var genres: List<Genre> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        )

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    fun replaceData(genres: List<Genre>) {
        this.genres = genres
        notifyDataSetChanged()
    }

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(genre: Genre) {
            with(itemView) {
                text_genre.text = genre.name

                setOnClickListener {
                    listener.onGenreClick(genre)
                }
            }
        }
    }

    interface Listener {
        fun onGenreClick(genre: Genre)
    }
}