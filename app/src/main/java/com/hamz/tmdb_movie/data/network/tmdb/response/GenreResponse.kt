package com.hamz.tmdb_movie.data.network.tmdb.response

import com.hamz.tmdb_movie.data.model.Genre

data class GenreResponse(
    val genres: List<Genre>
)