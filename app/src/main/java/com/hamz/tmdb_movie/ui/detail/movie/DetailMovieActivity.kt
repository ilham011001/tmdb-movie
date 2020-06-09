package com.hamz.tmdb_movie.ui.detail.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hamz.tmdb_movie.R
import com.hamz.tmdb_movie.ui.detail.review.ReviewMovieActivity
import com.hamz.tmdb_movie.util.LoadingState
import com.hamz.tmdb_movie.util.Tmdb
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.item_movie.text_rate
import kotlinx.android.synthetic.main.item_movie.text_title
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieActivity : AppCompatActivity() {

    private val API_KEY = Tmdb.API_KEY

    private val mDetailMovieViewModel: DetailMovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val movieId = intent.getLongExtra("movie_id", 0)
        Log.e("detail", "movie id : $movieId")
        mDetailMovieViewModel.getMovieDetail(API_KEY, movieId)

        initView()
        initObserveView()
    }

    private fun initView() {
        container_ui_detail.visibility = View.GONE
        progress_detail.visibility = View.VISIBLE

        image_back.setOnClickListener {
            finish()
        }

        btn_review.setOnClickListener {
            val intent = Intent(this, ReviewMovieActivity::class.java).apply {
                putExtra("movie_id", intent.getLongExtra("movie_id", 0))
            }
            startActivity(intent)
        }
    }

    private fun initObserveView() {
        mDetailMovieViewModel.movie.observe(this, Observer { movie ->
            if (movie != null) {
                text_title.text = movie.title
                text_rate.text = movie.voteAverage.toString()
                text_overview.text = movie.overview

                val stringBuilder = StringBuilder()
                movie.genres.forEach { genre ->
                    stringBuilder.append("${genre.name} ")
                }
                text_genre.text = stringBuilder.toString()

                Glide
                    .with(this)
                    .load("${Tmdb.IMAGE_URL}${movie.posterPath}")
                    .into(image_poster)
            }
        })

        mDetailMovieViewModel.loadingState.observe(this, Observer { state ->
            when (state.status) {
                LoadingState.Status.SUCCESS -> {
                    container_ui_detail.visibility = View.VISIBLE
                    progress_detail.visibility = View.GONE
                }
                LoadingState.Status.FAILED -> {
                    Toast.makeText(
                        applicationContext,
                        state.msg,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}