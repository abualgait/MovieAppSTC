package com.stc.muhammadsayed.cache.model


import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.domain.util.DomainMapper


class GenreEntityMapper : DomainMapper<GenreEntity, Genre> {
    override fun mapToDomainModel(model: GenreEntity): Genre {
        return Genre(
            id = model.id,
            name = model.name
        )
    }

    override fun mapFromDomainModel(domainModel: Genre): GenreEntity {
        return GenreEntity(
            id = domainModel.id,
            name = domainModel.name
        )
    }

    fun fromEntityList(initial: List<GenreEntity>): List<Genre> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Genre>): List<GenreEntity> {
        return initial.map { mapFromDomainModel(it) }
    }


}






