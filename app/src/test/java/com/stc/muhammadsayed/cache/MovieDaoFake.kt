package com.stc.muhammadsayed.cache

import com.stc.muhammadsayed.cache.model.MovieEntity


class MovieDaoFake(
    private val appDatabaseFake: AppDatabaseFake
) : MovieDao {
    override suspend fun insertMovie(movie: MovieEntity): Long {
        appDatabaseFake.movies.add(movie)
        return 1 // return success
    }

    override suspend fun insertMovies(movies: List<MovieEntity>): LongArray {
        appDatabaseFake.movies.addAll(movies)
        return LongArray(1) //return success
    }

    override suspend fun getMovieById(id: Int): MovieEntity? {
        return appDatabaseFake.movies.find { it.id == id } //return success
    }

    override suspend fun deleteMovies(ids: List<Int>): Int {
     return 1
    }

    override suspend fun deleteMovies(primaryKey: Int): Int {
       return 1
    }

    override suspend fun deleteAllMovies() {

    }

    override suspend fun searchMovies(query: String, page: Int, pageSize: Int): List<MovieEntity> {
        return appDatabaseFake.movies //return success
    }

    override suspend fun getAllMovies(page: Int, pageSize: Int): List<MovieEntity> {
        return appDatabaseFake.movies //return success
    }


}