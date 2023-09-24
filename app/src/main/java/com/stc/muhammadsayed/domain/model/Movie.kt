package com.stc.muhammadsayed.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.stc.muhammadsayed.di.NetworkModule
import com.stc.muhammadsayed.util.movieDisplayDateFormat
import com.stc.muhammadsayed.util.movieDisplayDateYearFormat
import com.stc.muhammadsayed.util.movieResponseDateFormat
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.ParseException

@Parcelize
data class Movie(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    @SerializedName("genres")
    var genres: List<Genre>? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    val status: String? = null,
    val release_date: String? = null,
    val media_type: String? = null,
    val id: Int? = null,
    @SerializedName("media_type")
    val mediaType: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String? = null,
    @SerializedName("release_date", alternate = ["first_air_date"])
    val releaseDate: String? = null,
    val runtime: Int? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
) : Parcelable {


    /**
     * http link to the image in The API
     * val imageUrl get() = "https://image.tmdb.org/t/p/w780/$backdrop_path?"
     */
    @IgnoredOnParcel
    val imageUrl: String
        get() = String.format(NetworkModule.POSTER_PATH, backdrop_path)

    @IgnoredOnParcel
    val posterImage: String
        get() = String.format(NetworkModule.POSTER_PATH, poster_path)

    @IgnoredOnParcel
    val rating: String
        get() = String.format("%s Rating ", vote_average)


    @IgnoredOnParcel
    val date: String
        get() {
            return try {
                releaseDate?.let {
                    movieResponseDateFormat.parse(releaseDate)
                        ?.let { movieDisplayDateFormat.format(it) } ?: "N/A"
                } ?: ""
            } catch (pe: ParseException) {
                ""
            }
        }

    @IgnoredOnParcel
    val year: String
        get() {
            return try {
                releaseDate?.let {
                    movieResponseDateFormat.parse(releaseDate)
                        ?.let { movieDisplayDateYearFormat.format(it) } ?: "N/A"
                } ?: ""
            } catch (pe: ParseException) {
                ""
            }
        }

    @IgnoredOnParcel
    val runTime: String
        get() = String.format("%s min", runtime)


}


