package com.stc.muhammadsayed.cache.model


import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.domain.util.DomainMapper


class MovieEntityMapper : DomainMapper<MovieEntity, Movie> {
    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            id = model.id,
            genreIds = model.genreIds,
            backdrop_path = model.backdropPath,
            media_type = model.mediaType,
            overview = model.overview,
            original_title = model.originalTitle,
            original_language = model.originalLanguage,
            title = model.title,
            popularity = model.popularity,
            poster_path = model.posterPath,
            releaseDate = model.releaseDate,
            vote_count = model.voteCount,
            vote_average = model.voteAverage)
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            id = domainModel.id,
            genreIds = domainModel.genreIds ?: emptyList(),
            backdropPath = domainModel.backdrop_path,
            mediaType = domainModel.media_type,
            originalLanguage = domainModel.original_language,
            originalTitle = domainModel.original_title,
            overview = domainModel.overview,
            popularity = domainModel.popularity,
            posterPath = domainModel.poster_path,
            releaseDate = domainModel.releaseDate,
            title = domainModel.title,
            voteAverage = domainModel.vote_average,
            voteCount = domainModel.vote_count
        )
    }

    fun fromEntityList(initial: List<MovieEntity>): List<Movie> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Movie>): List<MovieEntity> {
        return initial.map { mapFromDomainModel(it) }
    }


}






