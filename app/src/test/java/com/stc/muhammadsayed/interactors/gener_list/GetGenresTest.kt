package com.stc.muhammadsayed.interactors.gener_list

import com.google.gson.GsonBuilder
import com.stc.muhammadsayed.cache.AppDatabaseFake
import com.stc.muhammadsayed.cache.GenreDao
import com.stc.muhammadsayed.cache.GenreDaoFake
import com.stc.muhammadsayed.cache.model.GenreEntityMapper
import com.stc.muhammadsayed.data.MockWebServerResponse.genreListResponse
import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.interactors.genre_list.GetGenres
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.GenreDtoMapper
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

class GetGenresTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabase = AppDatabaseFake()
    private val DUMMY_TOKEN = "dummy_token_for_testing"

    //system in test
    private lateinit var getGenres: GetGenres

    //Dependencies
    private lateinit var retrofitService: RetrofitService
    private lateinit var genreDao: GenreDao
    private val dtoMapper = GenreDtoMapper()
    private val entityMapper = GenreEntityMapper()


    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/genre/movie/list/")
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
        genreDao = GenreDaoFake(appDatabaseFake = appDatabase)

        //instantiate the system in test
        getGenres = GetGenres(
            genreDao = genreDao,
            retrofitService = retrofitService,
            dtoMapper = dtoMapper,
            entityMapper = entityMapper

        )
    }

    /**
     * 1. Are the genres retrieved from the network?
     * 2. Are the genres inserted into the cache?
     * 3. Are the genres then emitted as a FLOW from the cache to the UI
     */

    @Test
    fun getGernesFromNetwork_emitGenresFromCache(): Unit = runBlocking {
        //condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(genreListResponse)
        )
        //confirm the cache is empty to start

        assert(genreDao.getAllGenres().isEmpty())

        val flowItem = getGenres.execute(
            key = DUMMY_TOKEN, true
        ).toList()

        // confirm the cache is not empty
        assert(genreDao.getAllGenres().isNotEmpty())

        //first emission should be LOADING
        assert(flowItem[0].loading)

        // second emission should be the list of genres
        val genres = flowItem[1].data
        assert((genres?.size ?: 0) > 0)

        //confirm they are actually Genre objects
        assert(genres?.get(index = 0) is Genre)

        //Ensure loading is false now
        assert(!flowItem[1].loading)
    }

    @Test
    fun getGenresFromNetwork_emitHttpError(): Unit = runBlocking {
        //condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = getGenres.execute(
            DUMMY_TOKEN, true
        ).toList()

        assert(flowItems[0].loading)

        val error = flowItems[1].error
        assert(error != null)

        assert(!flowItems[1].loading)
    }


    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}