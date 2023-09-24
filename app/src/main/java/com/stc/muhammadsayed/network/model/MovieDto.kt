package com.stc.muhammadsayed.network.model


import com.google.gson.annotations.SerializedName
import com.stc.muhammadsayed.domain.model.Genre


class MovieDto(

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null,

    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("media_type")
    val mediaType: String? = null,

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("popularity")
    val popularity: Double? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("runtime")
    val runtime: Int? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("vote_count")
    val voteCount: Int? = null
)