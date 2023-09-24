package com.stc.muhammadsayed.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stc.muhammadsayed.cache.model.MovieEntity
import com.stc.muhammadsayed.util.PAGE_SIZE

@Dao
interface MovieDao {


    @Insert
    suspend fun insertMovie(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<MovieEntity>): LongArray

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("DELETE FROM movies WHERE id IN (:ids)")
    suspend fun deleteMovies(ids: List<Int>): Int

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM movies WHERE id = :primaryKey")
    suspend fun deleteMovies(primaryKey: Int): Int

    /**
     * Retrieve movies for a particular page.
     * Ex: page = 2 retrieves movies from 20-40.
     * Ex: page = 3 retrieves movies from 40-80
     */
    @Query(
        """
        SELECT * FROM movies
        WHERE title LIKE '%' || :query || '%'
        OR overview LIKE '%' || :query || '%'
        ORDER BY release_date DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
        """
    )
    suspend fun searchMovies(
        query: String,
        page: Int,
        pageSize: Int = PAGE_SIZE
    ): List<MovieEntity>

    /**
     * Same as 'searchMovies' function, but no query.
     */
    @Query(
        """
        SELECT * FROM movies 
        ORDER BY release_date DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """
    )
    suspend fun getAllMovies(
        page: Int,
        pageSize: Int = PAGE_SIZE
    ): List<MovieEntity>

}