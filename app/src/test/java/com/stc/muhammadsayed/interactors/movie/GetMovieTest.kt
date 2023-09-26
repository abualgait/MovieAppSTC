package com.stc.muhammadsayed.interactors.movie

import com.google.gson.GsonBuilder
import com.stc.muhammadsayed.data.MockWebServerResponse
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.interactors.movie_detail.GetMovie
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.MovieDtoMapper
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetMovieTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    //system in test
    private lateinit var getMovie: GetMovie
    private val MOVIE_ID = 460465


    //Dependencies
    private lateinit var retrofitService: RetrofitService
    private val dtoMapper = MovieDtoMapper()


    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/3/")
        retrofitService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .create()
                )
            )
            .build()
            .create(RetrofitService::class.java)


        //instantiate the system in test
        getMovie = GetMovie(
            movieDtoMapper = dtoMapper,
            retrofitService = retrofitService
        )
    }

    @Test
    fun getMoviesFromNetwork_getMovieById(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponse.movieWithId460465)
        )
        // run use case
        val movieAsFlow = getMovie.execute(MOVIE_ID, true).toList()

        // first emission should be `loading`
        assert(movieAsFlow[0].loading)

        // second emission should be the movie
        val movie = movieAsFlow[1].data
        assert(movie?.id == MOVIE_ID)

        // confirm it is actually a Movie object
        assert(movie is Movie)

        // 'loading' should be false now
        assert(!movieAsFlow[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

}