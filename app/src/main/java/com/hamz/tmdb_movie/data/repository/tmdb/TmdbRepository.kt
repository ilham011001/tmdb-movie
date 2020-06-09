package com.hamz.tmdb_movie.data.repository.tmdb

import com.hamz.tmdb_movie.data.model.Genre
import com.hamz.tmdb_movie.data.model.Movie
import com.hamz.tmdb_movie.data.network.tmdb.response.GenreResponse
import com.hamz.tmdb_movie.data.network.tmdb.response.MovieResponse
import com.hamz.tmdb_movie.data.network.tmdb.response.ReviewResponse

interface TmdbRepository {

    suspend fun getGenres(apiKey: String): GenreResponse

    suspend fun getMovies(
        apiKey: String,
        genre: Int,
        page: Int
    ): MovieResponse

    suspend fun getMovieDetail(
        apiKey: String,
        movieId: Long
    ): Movie

    suspend fun getReviews(
        apiKey: String,
        movieId: Long,
        page: Int
    ): ReviewResponse
}