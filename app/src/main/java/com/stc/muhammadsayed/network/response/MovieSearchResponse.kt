package com.stc.muhammadsayed.network.response


import com.google.gson.annotations.SerializedName
import com.stc.muhammadsayed.network.model.MovieDto

data class MovieSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Double
)