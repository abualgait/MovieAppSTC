package com.stc.muhammadsayed.util


import com.stc.muhammadsayed.domain.model.Genre


fun List<Genre>.genreToCommaSeparatedString(): String {
    return this.joinToString(", ", transform = { it.name ?: "" })
}


