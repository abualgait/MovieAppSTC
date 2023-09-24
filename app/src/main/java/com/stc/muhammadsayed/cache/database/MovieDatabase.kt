package com.stc.muhammadsayed.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stc.muhammadsayed.cache.GenreDao
import com.stc.muhammadsayed.cache.MovieDao
import com.stc.muhammadsayed.cache.model.Converters
import com.stc.muhammadsayed.cache.model.GenreEntity
import com.stc.muhammadsayed.cache.model.MovieEntity


@Database(entities = [MovieEntity::class, GenreEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao

    companion object {
        const val DATABASE_NAME = "movie_db"
    }
}