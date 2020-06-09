package com.hamz.tmdb_movie.data.network.tmdb.response

import com.google.gson.annotations.SerializedName
import com.hamz.tmdb_movie.data.model.Movie

data class MovieResponse(
    val page: Int,

    @SerializedName("results")
    val movies: ArrayList<Movie?>,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int
)