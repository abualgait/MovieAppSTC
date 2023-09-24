package com.stc.muhammadsayed.di


import com.google.gson.GsonBuilder
import com.stc.muhammadsayed.network.RetrofitService
import com.stc.muhammadsayed.network.model.GenreDtoMapper
import com.stc.muhammadsayed.network.model.MovieDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val THUMB_NAIL_PATH = "https://image.tmdb.org/t/p/w500/%s"
    const val POSTER_PATH = "https://image.tmdb.org/t/p/original/%s"

    @Singleton
    @Provides
    fun provideMovieMapper(): MovieDtoMapper {
        return MovieDtoMapper()
    }

    @Singleton
    @Provides
    fun provideGenreMapper(): GenreDtoMapper {
        return GenreDtoMapper()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun provideMovieService(okHttpClient: OkHttpClient): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()
            .create(RetrofitService::class.java)
    }


    @Singleton
    @Provides
    @Named("api_key")
    fun provideAuthKey(): String {
        return "6e988baf411d864f103a85623fdb93df"
    }

}