package com.stc.muhammadsayed.network.model

import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.domain.util.DomainMapper


class GenreDtoMapper : DomainMapper<GenreDTO, Genre> {


    override fun mapToDomainModel(model: GenreDTO): Genre {
        return Genre(
            id = model.id,
            name = model.name
        )
    }

    /**
     * We use this function when publishing to the network
     */
    override fun mapFromDomainModel(domainModel: Genre): GenreDTO {
        return GenreDTO(
            id = domainModel.id,
            name = domainModel.name
        )

    }

    fun toDomainList(initial: List<GenreDTO>): List<Genre> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Genre>): List<GenreDTO> {
        return initial.map { mapFromDomainModel(it) }
    }


}



