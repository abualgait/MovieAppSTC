package com.stc.muhammadsayed.interactors.movie_detail

import android.util.Log
import com.stc.muhammadsayed.domain.data.DataState
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.MovieDtoMapper
import com.stc.muhammadsayed.util.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMovie(
    private val retrofitService: RetrofitService,
    private val movieDtoMapper: MovieDtoMapper
) {
    fun execute(
        movieId: Int,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<Movie>> = flow {
        try {
            emit(DataState.loading<Movie>())

            if (isNetworkAvailable) {
                //get movie from network
                val networkMovie = getMovieFromNetwork(movieId) //dto -> domain

                emit(DataState.success(networkMovie))

            }

        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<Movie>(e.message ?: "Unknown error"))
        }
    }

    private suspend fun getMovieFromNetwork(movieId: Int): Movie {
        return movieDtoMapper.mapToDomainModel(retrofitService.getMovieDetail(movieId))
    }


}