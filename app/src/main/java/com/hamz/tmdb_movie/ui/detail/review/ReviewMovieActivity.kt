package com.hamz.tmdb_movie.ui.detail.review

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
import com.hamz.tmdb_movie.data.model.Review
import com.hamz.tmdb_movie.ui.movie.MovieActivity
import com.hamz.tmdb_movie.util.LoadingState
import com.hamz.tmdb_movie.util.RecyclerViewLoadMoreScroll
import com.hamz.tmdb_movie.util.Tmdb
import kotlinx.android.synthetic.main.activity_review_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewMovieActivity : AppCompatActivity(), ReviewAdapter.Listener {

    private val TAG = MovieActivity::class.simpleName
    private val API_KEY = Tmdb.API_KEY

    private val mReviewViewModel: ReviewViewModel by viewModel()
    private lateinit var mReviewAdapter: ReviewAdapter

    // scrolling
    private lateinit var mScrollListener: RecyclerViewLoadMoreScroll
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private var movieId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_movie)

        movieId = intent.getLongExtra("movie_id", 0)
        Log.e(TAG, "movie id: $movieId")

        mReviewViewModel.getReviews(API_KEY, movieId)

        initView()
        initObserveView()
        initRecyclerView()
    }

    private fun initView() {
        title = "Review"
    }

    private fun initRecyclerView() {
        mReviewAdapter = ReviewAdapter(this)
        mLayoutManager = LinearLayoutManager(this)
        mScrollListener = RecyclerViewLoadMoreScroll(mLayoutManager as LinearLayoutManager)

        mScrollListener.setOnLoadMoreListener(object :
            RecyclerViewLoadMoreScroll.OnLoadMoreListener {
            override fun onLoadMore() {
                loadMore()
            }
        })

        val divider =
            DividerItemDecoration(this@ReviewMovieActivity, DividerItemDecoration.VERTICAL)
        divider.setDrawable(resources.getDrawable(R.drawable.divider_white))

        recycler_review.apply {
            adapter = mReviewAdapter
            layoutManager = mLayoutManager
            addOnScrollListener(mScrollListener)
            setHasFixedSize(true)
            addItemDecoration(divider)
        }
    }

    private fun initObserveView() {
        mReviewViewModel.reviews.observe(this, Observer { reviews ->
            if (reviews != null) {
                mReviewAdapter.removeLoadingView()
                mReviewAdapter.addData(reviews)
                recycler_review.post {
                    mReviewAdapter.notifyDataSetChanged()
                }

                if (reviews.size == 0) {
                    Toast.makeText(this, "no review", Toast.LENGTH_SHORT).show()
                }
            }
        })

        mReviewViewModel.totalPages.observe(this, Observer { totalPages ->
            val currentPage = mReviewViewModel.currentPage
            if (currentPage <= totalPages) mScrollListener.setLoaded()

            Log.e("debug", "currentPage: $currentPage, totalPage: $totalPages")
        })

        mReviewViewModel.loadingState.observe(this, Observer { state ->
            when (state.status) {
                LoadingState.Status.SUCCESS -> progress_review.visibility = View.GONE
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

    private fun loadMore() {
        mReviewAdapter.addLoadingView()
        mReviewViewModel.getReviews(API_KEY, movieId)
    }

    override fun onReviewClick(review: Review?) {
        val intent = Intent(this, WebviewReviewActivity::class.java).apply {
            putExtra("url", review?.url)
        }
        startActivity(intent)
    }
}