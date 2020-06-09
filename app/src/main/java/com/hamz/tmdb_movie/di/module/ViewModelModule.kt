package com.hamz.tmdb_movie.di.module

import com.hamz.tmdb_movie.ui.detail.movie.DetailMovieViewModel
import com.hamz.tmdb_movie.ui.detail.review.ReviewViewModel
import com.hamz.tmdb_movie.ui.genre.GenreViewModel
import com.hamz.tmdb_movie.ui.movie.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { GenreViewModel(get(named("tmdbservice_cloud"))) }

    viewModel { MovieViewModel(get(named("tmdbservice_cloud"))) }

    viewModel { DetailMovieViewModel(get(named("tmdbservice_cloud"))) }

    viewModel { ReviewViewModel(get(named("tmdbservice_cloud"))) }
}