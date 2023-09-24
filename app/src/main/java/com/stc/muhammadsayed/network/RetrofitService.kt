package com.stc.muhammadsayed.network

import com.stc.muhammadsayed.network.model.MovieDto
import com.stc.muhammadsayed.network.response.GenresListResponse
import com.stc.muhammadsayed.network.response.MovieSearchResponse
import com.stc.muhammadsayed.util.MOVIE_DETAILS_URL
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") key: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int
    ): MovieSearchResponse


    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") key: String,
    ): GenresListResponse


    @GET(MOVIE_DETAILS_URL)
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
    ): MovieDto


}