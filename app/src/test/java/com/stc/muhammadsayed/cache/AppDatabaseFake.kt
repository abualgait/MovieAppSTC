package com.stc.muhammadsayed.cache

import com.stc.muhammadsayed.cache.model.GenreEntity
import com.stc.muhammadsayed.cache.model.MovieEntity

class AppDatabaseFake {

    val movies = mutableListOf<MovieEntity>()
    val genre = mutableListOf<GenreEntity>()
}