package com.hamz.tmdb_movie.data.repository.tmdb

import com.hamz.tmdb_movie.data.model.Genre
import com.hamz.tmdb_movie.data.model.Movie
import com.hamz.tmdb_movie.data.network.SafeApiRequest
import com.hamz.tmdb_movie.data.network.tmdb.TmdbService
import com.hamz.tmdb_movie.data.network.tmdb.response.GenreResponse
import com.hamz.tmdb_movie.data.network.tmdb.response.MovieResponse
import com.hamz.tmdb_movie.data.network.tmdb.response.ReviewResponse

class TmdbRepositoryImpl(
    private val tmdbService: TmdbService
) : SafeApiRequest(), TmdbRepository {

    override suspend fun getGenres(apiKey: String): GenreResponse {
        return apiRequest {
            tmdbService.getGenres(apiKey)
        }
    }

    override suspend fun getMovies(apiKey: String, genre: Int, page: Int): MovieResponse {
        return apiRequest {
            tmdbService.getMovies(
                apiKey = apiKey,
                genre = genre,
                page = page
            )
        }
    }

    override suspend fun getMovieDetail(apiKey: String, movieId: Long): Movie {
        return apiRequest {
            tmdbService.getMovieDetail(
                apiKey = apiKey,
                movieId = movieId
            )
        }
    }

    override suspend fun getReviews(apiKey: String, movieId: Long, page: Int): ReviewResponse {
        return apiRequest {
            tmdbService.getReviews(
                apiKey = apiKey,
                movieId = movieId,
                page = page
            )
        }
    }
}