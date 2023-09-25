package com.stc.muhammadsayed.presentation.ui.movie


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.stc.muhammadsayed.presentation.components.CircularIndeterminateProgressBar
import com.stc.muhammadsayed.presentation.components.MovieView
import com.stc.muhammadsayed.presentation.components.NothingHere
import com.stc.muhammadsayed.presentation.theme.AppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@Composable
fun MovieDetailScreen(
    isDarkTheme: Boolean,
    isNetworkAvailable: Boolean,
    movieId: Int?,
    viewModel: MovieDetailViewModel,
    onBackPressed: () -> Unit
) {

    if (movieId == null) {
        NothingHere()
    } else {
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(MovieEvent.GetMovieEvent(movieId))

        }
        val loading = viewModel.loading.value
        val movie = viewModel.movie.value
        val dialogQueue = viewModel.dialogQueue

        AppTheme(
            darkTheme = isDarkTheme,
            isNetworkAvailable = isNetworkAvailable,
            displayProgressBar = loading,
            dialogQueue = dialogQueue.queue.value

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                movie?.let {
                    MovieView(
                        movie = it,
                        onBackPressed = onBackPressed
                    )
                } ?: run {
                    if (loading) {
                        CircularIndeterminateProgressBar(true)
                    }
                }

            }

        }
    }

}




