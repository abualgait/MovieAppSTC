package com.stc.muhammadsayed.presentation.ui.movie

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.interactors.movie_detail.GetMovie
import com.stc.muhammadsayed.presentation.ui.util.DialogQueue
import com.stc.muhammadsayed.presentation.util.ConnectivityManager
import com.stc.muhammadsayed.presentation.util.property
import com.stc.muhammadsayed.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(
    private val getMovie: GetMovie,
    private val connectivityManager: ConnectivityManager,
    state: SavedStateHandle,
) : ViewModel() {

    val movie: MutableState<Movie?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val onLoad: MutableState<Boolean> = mutableStateOf(false)
    val dialogQueue = DialogQueue()
    var movieId: Int by state.property(-1)


    fun onTriggerEvent(event: MovieEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is MovieEvent.GetMovieEvent -> {
                        if (movie.value == null) {
                            getMovieDetails(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    private fun getMovieDetails(id: Int) {
        getMovie.execute(id, connectivityManager.isNetworkAvailable.value).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { data ->
                movie.value = data
                movieId = data.id ?: -1
            }
            dataState.error?.let { error ->
                Log.e(TAG, "getMovie: $error")
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)
    }


}