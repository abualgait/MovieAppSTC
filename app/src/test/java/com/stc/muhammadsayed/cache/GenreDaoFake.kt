package com.stc.muhammadsayed.cache

import com.stc.muhammadsayed.cache.model.GenreEntity


class GenreDaoFake(
    private val appDatabaseFake: AppDatabaseFake
) : GenreDao {
    override suspend fun insertGenre(genre: GenreEntity): Long {
        appDatabaseFake.genre.add(genre)
        return 1
    }

    override suspend fun insertGenres(genres: List<GenreEntity>): LongArray {
        appDatabaseFake.genre.addAll(genres)
        return LongArray(1)
    }

    override suspend fun getGenreById(id: Int): GenreEntity? {
        return appDatabaseFake.genre.find { it.id == id }
    }

    override suspend fun deleteGenres(ids: List<Int>): Int {
        return 1
    }

    override suspend fun deleteGenres(primaryKey: Int): Int {
        return 1
    }

    override suspend fun deleteAllGenres() {

    }

    override suspend fun getAllGenres(): List<GenreEntity> {
        return appDatabaseFake.genre
    }


}