package com.hamz.tmdb_movie.ui.genre

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamz.tmdb_movie.data.model.Genre
import com.hamz.tmdb_movie.data.repository.tmdb.TmdbRepository
import com.hamz.tmdb_movie.util.ApiException
import com.hamz.tmdb_movie.util.LoadingState
import com.hamz.tmdb_movie.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GenreViewModel(
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    private val TAG = GenreViewModel::class.java.simpleName

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>>
        get() = _genres

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState


    fun getGenres(apiKey: String) {
        _loadingState.postValue(LoadingState.LOADING)

        CoroutineScope(IO).launch {
            try {
                val response = tmdbRepository.getGenres(apiKey)
                _genres.postValue(response.genres)
                _loadingState.postValue(LoadingState.LOADED)

                Log.e(TAG, "response genre size: ${response.genres.size}")
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