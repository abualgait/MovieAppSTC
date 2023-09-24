package com.stc.muhammadsayed.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stc.muhammadsayed.cache.model.GenreEntity

@Dao
interface GenreDao {


    @Insert
    suspend fun insertGenre(genre: GenreEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: List<GenreEntity>): LongArray

    @Query("SELECT * FROM genres WHERE id = :id")
    suspend fun getGenreById(id: Int): GenreEntity?

    @Query("DELETE FROM genres WHERE id IN (:ids)")
    suspend fun deleteGenres(ids: List<Int>): Int

    @Query("DELETE FROM genres")
    suspend fun deleteAllGenres()

    @Query("DELETE FROM genres WHERE id = :primaryKey")
    suspend fun deleteGenres(primaryKey: Int): Int


    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<GenreEntity>

}