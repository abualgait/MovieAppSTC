package com.stc.muhammadsayed.presentation.ui.movie_list

sealed class GenreListEvent {
    object GetGenresList : GenreListEvent()
}