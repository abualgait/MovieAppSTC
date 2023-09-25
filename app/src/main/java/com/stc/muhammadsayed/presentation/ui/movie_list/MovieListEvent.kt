package com.stc.muhammadsayed.presentation.ui.movie_list

sealed class MovieListEvent {
    object NewSearchEvent : MovieListEvent()
    object NextPageEvent : MovieListEvent()
}