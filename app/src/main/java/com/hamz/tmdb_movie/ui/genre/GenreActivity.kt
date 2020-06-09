package com.hamz.tmdb_movie.ui.genre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamz.tmdb_movie.R
import com.hamz.tmdb_movie.data.model.Genre
import com.hamz.tmdb_movie.data.repository.tmdb.TmdbRepositoryImpl
import com.hamz.tmdb_movie.ui.movie.MovieActivity
import com.hamz.tmdb_movie.util.LoadingState
import com.hamz.tmdb_movie.util.Tmdb
import kotlinx.android.synthetic.main.activity_genre.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenreActivity : AppCompatActivity(), GenreAdapter.Listener {

    private val TAG = GenreActivity::class.simpleName
    private val API_KEY = Tmdb.API_KEY

    private val mGenreViewModel: GenreViewModel by viewModel()
    private lateinit var mGenreAdapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)

        mGenreViewModel.getGenres(API_KEY)

        initObserveView()
        initRecyclerView()
        initSwipeRefreshLayout()
    }


    private fun initRecyclerView() {
        mGenreAdapter = GenreAdapter(this)

        recycler_genre.apply {
            layoutManager = GridLayoutManager(this@GenreActivity, 2)
            adapter = mGenreAdapter
        }
    }

    private fun initSwipeRefreshLayout() {
        swipe_genre.setOnRefreshListener {
            mGenreViewModel.getGenres(API_KEY)
        }
    }

    private fun initObserveView() {
        mGenreViewModel.genres.observe(this, Observer { genres ->
            if (genres != null) {
                mGenreAdapter.replaceData(genres)
            }
        })

        mGenreViewModel.loadingState.observe(this, Observer { state ->
            when (state.status) {
                LoadingState.Status.SUCCESS -> swipe_genre.isRefreshing = false
                LoadingState.Status.RUNNING -> swipe_genre.isRefreshing = true
                LoadingState.Status.FAILED -> {
                    Toast.makeText(
                        applicationContext,
                        state.msg,
                        Toast.LENGTH_SHORT
                    ).show()
                    swipe_genre.isRefreshing = false
                }
            }
        })
    }

    override fun onGenreClick(genre: Genre) {
        val intent = Intent(this, MovieActivity::class.java).apply {
            putExtra("genre_id", genre.id)
            putExtra("name", genre.name)
        }
        startActivity(intent)
    }
}