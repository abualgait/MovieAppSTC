package com.stc.muhammadsayed.interactors.movie_detail

import android.util.Log
import com.stc.muhammadsayed.cache.MovieDao
import com.stc.muhammadsayed.cache.model.MovieEntityMapper
import com.stc.muhammadsayed.domain.data.DataState
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.MovieDtoMapper
import com.stc.muhammadsayed.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovie(
    private val movieDao: MovieDao,
    private val retrofitService: RetrofitService,
    private val entityMapper: MovieEntityMapper,
    private val movieDtoMapper: MovieDtoMapper
) {
    fun execute(
        movieId: Int,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<Movie>> = flow {
        try {
            emit(DataState.loading<Movie>())
            delay(1000)

            var movie = getMovieFromCache(movieId = movieId)
            if (movie != null) {
                emit(DataState.success(movie))
            } else {
                if (isNetworkAvailable) {
                    //get movie from network
                    val networkMovie = getMovieFromNetwork(movieId) //dto -> domain

                    //insert into cache
                    movieDao.insertMovie(
                        entityMapper.mapFromDomainModel(networkMovie)
                    )

                }

                movie = getMovieFromCache(movieId)
                if (movie != null) {
                    emit(DataState.success(movie))
                } else {
                    throw Exception("Unable to get the movie from the cache.")
                }
            }


        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<Movie>(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getMovieFromCache(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)?.let { movieEntity ->
            entityMapper.mapToDomainModel(movieEntity)
        }
    }

    private suspend fun getMovieFromNetwork(movieId: Int): Movie {
        return movieDtoMapper.mapToDomainModel(retrofitService.getMovieDetail(movieId))
    }


}