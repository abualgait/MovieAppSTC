package com.stc.muhammadsayed.network.response


import com.google.gson.annotations.SerializedName
import com.stc.muhammadsayed.network.model.GenreDTO

data class GenresListResponse(
    @SerializedName("genres")
    val genres: List<GenreDTO>
)