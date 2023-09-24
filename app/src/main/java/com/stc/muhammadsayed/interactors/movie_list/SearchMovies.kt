package com.stc.muhammadsayed.interactors.movie_list

import com.stc.muhammadsayed.cache.MovieDao
import com.stc.muhammadsayed.cache.model.MovieEntityMapper
import com.stc.muhammadsayed.domain.data.DataState
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.MovieDtoMapper
import com.stc.muhammadsayed.util.PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchMovies(
    private val movieDao: MovieDao,
    private val retrofitService: RetrofitService,
    private val entityMapper: MovieEntityMapper,
    private val dtoMapper: MovieDtoMapper,

    ) {
    fun execute(
        key: String,
        page: Int,
        query: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<List<Movie>>> = flow {
        try {
            emit(DataState.loading<List<Movie>>())

            // for pagination sake
            delay(1000)

            //force error for testing
            if (query == "error") {
                throw Exception("Search FAILED")
            }

            // search locally if we have search query
            if (isNetworkAvailable && query.isBlank()) {

                // Convert: NetworkMovieEntity -> Movie -> MovieCacheEntity
                val movies = getMoviesFromNetwork(key = key, page = page)

                //insert into the cache
                movieDao.insertMovies(entityMapper.toEntityList(movies))
            }

            // query the cache
            val cacheResult = if (query.isBlank()) {
                movieDao.getAllMovies(
                    pageSize = PAGE_SIZE,
                    page = page
                )
            } else {
                movieDao.searchMovies(
                    query = query,
                    pageSize = PAGE_SIZE,
                    page = page
                )
            }

            //emit List<Movie> from the cache
            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))
        } catch (e: Exception) {
            emit(DataState.error<List<Movie>>(e.message ?: "Unknown error"))
        }
    }


    private suspend fun getMoviesFromNetwork(
        key: String,
        page: Int
    ): List<Movie> {
        return dtoMapper.toDomainList(
            retrofitService.getMovies(
                key = key,
                page = page,
            ).movies
        )

    }
}