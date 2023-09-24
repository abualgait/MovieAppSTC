package com.stc.muhammadsayed.util

import java.text.SimpleDateFormat
import java.util.Locale

const val TAG = "AppDebug"

//PAGINATION
const val PAGE_SIZE = 10

//for detail fragment
const val IMAGE_HEIGHT = 400

const val MOVIE_DETAILS_URL = "movie/{movie_id}?api_key=6e988baf411d864f103a85623fdb93df"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w780"
const val MOVIE_WEB_URL = "https://www.themoviedb.org/movie/"


val movieResponseDateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH)
val movieDisplayDateFormat = SimpleDateFormat("MMM, yyyy", Locale.ENGLISH)
val movieDisplayDateYearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)

// sate keys for various events
const val STATE_KEY_PAGE = "movie.state.page.key"
const val STATE_KEY_QUERY = "movie.state.query.key"
const val STATE_KEY_LIST_POSITION = "movie.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "movie.state.query.selected_category"
const val STATE_KEY_MOVIE = "movie.state.movie.key"

