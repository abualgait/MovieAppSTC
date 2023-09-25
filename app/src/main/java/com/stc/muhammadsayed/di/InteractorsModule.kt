package com.stc.muhammadsayed.di

import com.stc.muhammadsayed.cache.GenreDao
import com.stc.muhammadsayed.cache.MovieDao
import com.stc.muhammadsayed.cache.model.GenreEntityMapper
import com.stc.muhammadsayed.cache.model.MovieEntityMapper
import com.stc.muhammadsayed.interactors.genre_list.GetGenres
import com.stc.muhammadsayed.interactors.movie_detail.GetMovie
import com.stc.muhammadsayed.interactors.movie_list.SearchMovies
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.GenreDtoMapper
import com.stc.muhammadsayed.network.model.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchMovies(
        retrofitService: RetrofitService,
        movieDao: MovieDao,
        movieEntityMapper: MovieEntityMapper,
        movieDtoMapper: MovieDtoMapper,
    ): SearchMovies {
        return SearchMovies(
            retrofitService = retrofitService,
            movieDao = movieDao,
            dtoMapper = movieDtoMapper,
            entityMapper = movieEntityMapper
        )
    }


    @ViewModelScoped
    @Provides
    fun provideGetGenres(
        retrofitService: RetrofitService,
        genreDao: GenreDao,
        genreEntityMapper: GenreEntityMapper,
        genreDtoMapper: GenreDtoMapper,
    ): GetGenres {
        return GetGenres(
            retrofitService = retrofitService,
            genreDao = genreDao,
            dtoMapper = genreDtoMapper,
            entityMapper = genreEntityMapper
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetMoviesDetails(
        retrofitService: RetrofitService,
        movieDtoMapper: MovieDtoMapper,
    ): GetMovie {
        return GetMovie(
            retrofitService = retrofitService,
            movieDtoMapper = movieDtoMapper
        )
    }

}