package com.hamz.tmdb_movie.di.module

import com.hamz.tmdb_movie.data.repository.tmdb.TmdbRepository
import com.hamz.tmdb_movie.data.repository.tmdb.TmdbRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {

    single(named("tmdbservice_cloud")) { TmdbRepositoryImpl(get()) as TmdbRepository }

}