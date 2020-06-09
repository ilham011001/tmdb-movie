package com.hamz.tmdb_movie.di.module

import android.content.Context
import com.hamz.tmdb_movie.data.network.NetworkConnectionInterceptor
import com.hamz.tmdb_movie.data.network.tmdb.TmdbService
import com.hamz.tmdb_movie.util.Tmdb
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { provideTmdbService(get()) }

    single { provideRetrofit(get(), Tmdb.URL) }

    single { provideOkhttpClient(Tmdb.CONNECT_TIMEOUT, Tmdb.READ_TIMEOUT, androidContext()) }
}

fun provideOkhttpClient(connectTimeout: Long, readTimeout: Long, context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .addInterceptor(NetworkConnectionInterceptor(context))
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideTmdbService(retrofit: Retrofit): TmdbService {
    return retrofit.create(TmdbService::class.java)
}