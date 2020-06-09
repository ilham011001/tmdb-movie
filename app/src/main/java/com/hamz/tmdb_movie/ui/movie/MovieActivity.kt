package com.hamz.tmdb_movie.ui.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamz.tmdb_movie.R
import com.hamz.tmdb_movie.data.model.Movie
import com.hamz.tmdb_movie.ui.detail.movie.DetailMovieActivity
import com.hamz.tmdb_movie.util.LoadingState
import com.hamz.tmdb_movie.util.RecyclerViewLoadMoreScroll
import com.hamz.tmdb_movie.util.Tmdb
import kotlinx.android.synthetic.main.activity_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity(), MovieAdapter.Listener {

    private val TAG = MovieActivity::class.simpleName
    private val API_KEY = Tmdb.API_KEY

    private val mMovieViewModel: MovieViewModel by viewModel()
    private lateinit var mMovieAdapter: MovieAdapter

    // scrolling
    private lateinit var mScrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private var genreId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        genreId = intent.getIntExtra("genre_id", 0)
        Log.e(TAG, "genreId : $genreId")

        mMovieViewModel.getMovies(API_KEY, genreId)

        updateToolbarTitle()
        initObserveView()
        initRecyclerView()
    }

    private fun updateToolbarTitle() {
        val name = intent.getStringExtra("name")
        title = name
    }

    private fun initRecyclerView() {
        mMovieAdapter = MovieAdapter(this)
        mLayoutManager = LinearLayoutManager(this)
        mScrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)

        mScrollListener.setOnLoadMoreListener(object :
            RecyclerViewLoadMoreScroll.OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreMovies()
            }
        })

        recycler_movie.apply {
            adapter = mMovieAdapter
            layoutManager = mLayoutManager
            addOnScrollListener(mScrollListener)
            setHasFixedSize(true)
        }
    }

    private fun initObserveView() {
        mMovieViewModel.movies.observe(this, Observer { movies ->
            if (movies != null) {
                mMovieAdapter.removeLoadingView()
                mMovieAdapter.addData(movies)
                recycler_movie.post {
                    mMovieAdapter.notifyDataSetChanged()
                }
            }
        })

        mMovieViewModel.totalPages.observe(this, Observer { totalPages ->
            val currentPage = mMovieViewModel.currentPage
            if (currentPage <= totalPages) mScrollListener.setLoaded()

            Log.e("debug", "currentPage: $currentPage, totalPage: $totalPages")
        })

        mMovieViewModel.loadingState.observe(this, Observer { state ->
            when (state.status) {
                LoadingState.Status.SUCCESS -> progress_movie.visibility = View.GONE
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

    private fun loadMoreMovies() {
        mMovieAdapter.addLoadingView()
        mMovieViewModel.getMovies(API_KEY, genreId)
    }

    override fun onMovieClick(movie: Movie) {
        val intent = Intent(this, DetailMovieActivity::class.java).apply {
            putExtra("movie_id", movie.id)
        }
        startActivity(intent)
    }
}