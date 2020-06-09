package com.hamz.tmdb_movie

import android.app.Application
import com.hamz.tmdb_movie.di.module.networkModule
import com.hamz.tmdb_movie.di.module.repositoryModule
import com.hamz.tmdb_movie.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TmdbmovieApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TmdbmovieApp)
            androidLogger(Level.DEBUG)

            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}