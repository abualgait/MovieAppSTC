package com.stc.muhammadsayed.presentation.ui.movie

sealed class MovieEvent {
    data class GetMovieEvent(
        val id: Int
    ) : MovieEvent()
}