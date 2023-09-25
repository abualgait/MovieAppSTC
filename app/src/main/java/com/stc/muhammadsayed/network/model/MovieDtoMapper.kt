package com.stc.muhammadsayed.network.model

import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.domain.util.DomainMapper


class MovieDtoMapper : DomainMapper<MovieDto, Movie> {


    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            backdrop_path = model.backdropPath,
            genres = model.genres,
            genreIds = model.genreIds,
            id = model.id,
            original_language = model.originalLanguage,
            original_title = model.originalTitle,
            overview = model.overview,
            media_type = model.mediaType,
            popularity = model.popularity,
            poster_path = model.posterPath,
            releaseDate = model.releaseDate,
            runtime = model.runtime,
            title = model.title,
            vote_average = model.voteAverage,
            vote_count = model.voteCount,
            status = model.status,
            spokenLanguages = model.spokenLanguages,
            budget = model.budget,
            revenue = model.revenue,
            homepage = model.homepage,
        )
    }

    /**
     * We use this function when publishing to the network
     */
    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(

            backdropPath = domainModel.backdrop_path,
            genres = domainModel.genres,
            genreIds = domainModel.genreIds,
            id = domainModel.id,
            mediaType = domainModel.media_type,
            originalLanguage = domainModel.original_language,
            originalTitle = domainModel.original_title,
            overview = domainModel.overview,
            popularity = domainModel.popularity,
            posterPath = domainModel.poster_path,
            releaseDate = domainModel.releaseDate,
            runtime = domainModel.runtime,
            title = domainModel.title,
            voteAverage = domainModel.vote_average,
            voteCount = domainModel.vote_count,
            status = domainModel.status,
            spokenLanguages = domainModel.spokenLanguages,
            budget = domainModel.budget,
            revenue = domainModel.revenue,
            homepage = domainModel.homepage,
        )

    }

    fun toDomainList(initial: List<MovieDto>): List<Movie> {
        return initial.map { mapToDomainModel(it) }
    }

}



