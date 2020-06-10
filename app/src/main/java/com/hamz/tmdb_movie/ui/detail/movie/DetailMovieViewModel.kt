package com.hamz.tmdb_movie.ui.detail.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamz.tmdb_movie.data.model.Movie
import com.hamz.tmdb_movie.data.model.Review
import com.hamz.tmdb_movie.data.repository.tmdb.TmdbRepository
import com.hamz.tmdb_movie.util.ApiException
import com.hamz.tmdb_movie.util.LoadingState
import com.hamz.tmdb_movie.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    private val TAG = DetailMovieViewModel::class.simpleName

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    fun getMovieDetail(apiKey: String, movieId: Long) {
        _loadingState.postValue(LoadingState.LOADING)

        CoroutineScope(Main).launch {
            try {
                val response = tmdbRepository.getMovieDetail(apiKey, movieId)
                _movie.postValue(response)
                _loadingState.postValue(LoadingState.LOADED)

                Log.e(TAG, "response detail movie success")
                Log.e(TAG, "response videos size: ${response.videos.results.size}")
            } catch (e: ApiException) {
                Log.e(TAG, "ApiException: ${e.message}")
                _loadingState.postValue(LoadingState.error(e.message))
            } catch (e: NoInternetException) {
                Log.e(TAG, "NoInternetException: ${e.message}")
                _loadingState.postValue(LoadingState.error(e.message))
            }
        }
    }


}