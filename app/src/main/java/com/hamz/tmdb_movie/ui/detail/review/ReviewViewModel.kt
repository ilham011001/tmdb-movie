package com.hamz.tmdb_movie.ui.detail.review

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamz.tmdb_movie.data.model.Review
import com.hamz.tmdb_movie.data.repository.tmdb.TmdbRepository
import com.hamz.tmdb_movie.util.ApiException
import com.hamz.tmdb_movie.util.LoadingState
import com.hamz.tmdb_movie.util.NoInternetException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val tmdbRepository: TmdbRepository
) : ViewModel() {

    private val TAG = ReviewViewModel::class.simpleName

    private val _reviews = MutableLiveData<ArrayList<Review?>>()
    val reviews: LiveData<ArrayList<Review?>>
        get() = _reviews

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    private val _totalPages = MutableLiveData<Int>()
    val totalPages: LiveData<Int>
        get() = _totalPages

    var currentPage = 1

    fun getReviews(apiKey: String, movieId: Long) {
        _loadingState.postValue(LoadingState.LOADING)

        CoroutineScope(Main).launch {
            try {
                val response = tmdbRepository.getReviews(apiKey, movieId, currentPage)
                _reviews.postValue(response.reviews)
                _totalPages.postValue(response.totalPages)
                _loadingState.postValue(LoadingState.LOADED)

                currentPage++

                Log.e(TAG, "response review size: ${response.reviews.size}, total results: ${response.totalResults}, total pages: ${response.totalPages}")
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