package com.stc.muhammadsayed.util


import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.domain.model.SpokenLanguage


fun List<Genre>.genreToCommaSeparatedString(): String {
    return this.joinToString(", ", transform = { it.name ?: "" })
}

fun List<SpokenLanguage>.languageToCommaSeparatedString(): String {
    return this.joinToString(", ", transform = { it.englishName ?: "" })
}


