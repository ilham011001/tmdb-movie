package com.hamz.tmdb_movie.data.network.tmdb.response

import com.google.gson.annotations.SerializedName
import com.hamz.tmdb_movie.data.model.Review

data class ReviewResponse (
    val id: Int,
    val page: Int,

    @SerializedName("results")
    val reviews: ArrayList<Review?>,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int
)