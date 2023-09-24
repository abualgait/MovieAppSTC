package com.stc.muhammadsayed.interactors.genre_list

import com.stc.muhammadsayed.cache.GenreDao
import com.stc.muhammadsayed.cache.model.GenreEntityMapper
import com.stc.muhammadsayed.domain.data.DataState
import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.GenreDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetGenres(
    private val genreDao: GenreDao,
    private val retrofitService: RetrofitService,
    private val entityMapper: GenreEntityMapper,
    private val dtoMapper: GenreDtoMapper,
) {
    fun execute(
        key: String,
        isNetworkAvailable: Boolean,
    ): Flow<DataState<List<Genre>>> = flow {
        try {
            emit(DataState.loading<List<Genre>>())

            if (isNetworkAvailable) {
                val genres = getGenresFromNetwork(key = key)

                //insert into the cache
                genreDao.insertGenres(entityMapper.toEntityList(genres))
            }

            // query the cache
            val cacheResult = genreDao.getAllGenres()


            //emit List<Genre> from the cache
            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))
        } catch (e: Exception) {
            emit(DataState.error<List<Genre>>(e.message ?: "Unknown error"))
        }
    }


    private suspend fun getGenresFromNetwork(
        key: String
    ): List<Genre> {
        return dtoMapper.toDomainList(
            retrofitService.getGenres(
                key = key,
            ).genres
        )

    }
}