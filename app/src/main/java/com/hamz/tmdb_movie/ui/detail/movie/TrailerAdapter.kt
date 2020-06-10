package com.hamz.tmdb_movie.ui.detail.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamz.tmdb_movie.R
import com.hamz.tmdb_movie.data.model.Movie
import kotlinx.android.synthetic.main.item_trailer.view.*

class TrailerAdapter(private val listener: Listener) :
    RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    private var videos: ArrayList<Movie.Video> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder =
        TrailerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        )

    override fun getItemCount(): Int = videos.size

    fun replaceData(videos: ArrayList<Movie.Video>) {
        this.videos = videos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    inner class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(video: Movie.Video) {

            val thumbnailUrl = "https://img.youtube.com/vi/${video.key}/0.jpg"

            with(itemView) {
                Glide
                    .with(context)
                    .load(thumbnailUrl)
                    .into(image_thumbnail)

                setOnClickListener {
                    listener.onTrailerClick(video)
                }
            }
        }
    }

    interface Listener {
        fun onTrailerClick(video: Movie.Video)
    }
}