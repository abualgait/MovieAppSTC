package com.stc.muhammadsayed.di

import androidx.room.Room
import com.stc.muhammadsayed.cache.GenreDao
import com.stc.muhammadsayed.cache.MovieDao
import com.stc.muhammadsayed.cache.database.MovieDatabase
import com.stc.muhammadsayed.cache.model.GenreEntityMapper
import com.stc.muhammadsayed.cache.model.MovieEntityMapper
import com.stc.muhammadsayed.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {


    /**
     * Not recommended to be used in production coz it destroy cache data and session data
     * and rebuild it
     */

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): MovieDatabase {
        return Room.databaseBuilder(app, MovieDatabase::class.java, MovieDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(app: MovieDatabase): MovieDao {
        return app.movieDao()
    }

    @Singleton
    @Provides
    fun provideCacheMovieMapper(): MovieEntityMapper {
        return MovieEntityMapper()
    }


    @Singleton
    @Provides
    fun provideGenreDao(app: MovieDatabase): GenreDao {
        return app.genreDao()
    }

    @Singleton
    @Provides
    fun provideCacheGenreMapper(): GenreEntityMapper {
        return GenreEntityMapper()
    }


}