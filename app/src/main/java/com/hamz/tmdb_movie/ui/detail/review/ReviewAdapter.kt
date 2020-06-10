package com.hamz.tmdb_movie.ui.detail.review

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamz.tmdb_movie.R
import com.hamz.tmdb_movie.data.model.Review
import com.hamz.tmdb_movie.util.Constant
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter(private val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = ReviewAdapter::class.simpleName

    private var reviews: ArrayList<Review?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            ReviewViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
            )
        } else {
            LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.progress_loading, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            val holder = holder as ReviewViewHolder
            holder.bind(reviews[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (reviews[position] == null) {
            Constant.VIEW_TYPE_LOADING
        } else {
            Constant.VIEW_TYPE_ITEM
        }
    }

    fun addLoadingView() {
        Handler().post {
            reviews.add(null)
            notifyItemInserted(reviews.size - 1)
        }
    }

    fun removeLoadingView() {
        if (reviews.size != 0) {
            reviews.removeAt(reviews.size - 1)
            notifyItemRemoved(reviews.size)
        }
    }

    fun addData(reviews: ArrayList<Review?>) {
        this.reviews.addAll(reviews)
        notifyDataSetChanged()

        Log.e(TAG, "total review size: ${this.reviews.size}")
    }

    fun getReviewsSize(): Int = reviews.size

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: Review?) {
            with(itemView) {
                text_author.text = review?.author ?: ""
                text_content.text = review?.content ?: ""

                text_see.setOnClickListener {
                    listener.onReviewClick(review)
                }
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface Listener {
        fun onReviewClick(review: Review?)
    }
}