package com.stc.muhammadsayed.presentation.ui.movie_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.interactors.genre_list.GetGenres
import com.stc.muhammadsayed.interactors.movie_list.SearchMovies
import com.stc.muhammadsayed.presentation.ui.movie_list.MovieListEvent.*
import com.stc.muhammadsayed.presentation.ui.util.DialogQueue
import com.stc.muhammadsayed.presentation.util.ConnectivityManager
import com.stc.muhammadsayed.util.PAGE_SIZE
import com.stc.muhammadsayed.util.STATE_KEY_LIST_POSITION
import com.stc.muhammadsayed.util.STATE_KEY_PAGE
import com.stc.muhammadsayed.util.STATE_KEY_QUERY
import com.stc.muhammadsayed.util.STATE_KEY_SELECTED_CATEGORY
import com.stc.muhammadsayed.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
@ExperimentalCoroutinesApi
class MovieListViewModel
@Inject
constructor(
    private val searchMovies: SearchMovies,
    private val getGenres: GetGenres,
    private val connectivityManager: ConnectivityManager,
    @Named("api_key") private val key: String,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val movies: MutableState<List<Movie>> = mutableStateOf(ArrayList())
    val genres: MutableState<List<Genre>> = mutableStateOf(ArrayList())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<Genre?> = mutableStateOf(null)
    val loading = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var movieListScrollPosition = 0
    val dialogQueue = DialogQueue()


    init {

        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            Log.d(TAG, "restoring page: $p")
            setPage(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            Log.d(TAG, "restoring scroll position: $p")
            setListScrollPosition(p)
        }
        savedStateHandle.get<Genre>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        onTriggerEvent(NewSearchEvent)
        onTriggerEvent(GenreListEvent.GetGenresList)

    }

    private fun onTriggerEvent(event: GenreListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is GenreListEvent.GetGenresList -> {
                        getGenres()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
        }
    }

    fun onTriggerEvent(event: MovieListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }

                    is NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            } finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }


    private fun getGenres() {
        getGenres.execute(
            key = key, connectivityManager.isNetworkAvailable.value
        ).onEach { dataState ->
            dataState.data?.let { list ->
                genres.value = list
            }
            dataState.error?.let { error ->
                Log.e(TAG, "getGenres: $error")
                dialogQueue.appendErrorMessage("Error", error)
            }

        }.launchIn(viewModelScope)


    }

    //use case #1
    private fun newSearch() {
        Log.d(TAG, "newSearch : query: ${query.value}, page: ${page.value}")
        val selectedGenre = selectedCategory.value
        resetSearchState()
        searchMovies.execute(
            key = key,
            page = page.value,
            query =
            if (selectedGenre != null) "" else query.value,
            if (selectedGenre != null) false else connectivityManager.isNetworkAvailable.value
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { list ->
                if (selectedGenre != null) {
                    //filter by genre locally
                    val moviesInSelectedCategory = list.filter { movie ->
                        movie.genreIds?.contains(selectedGenre.id) == true
                    }
                    movies.value = moviesInSelectedCategory
                } else {
                    movies.value = list
                }

            }
            dataState.error?.let { error ->
                Log.e(TAG, "newSearch: $error")
                dialogQueue.appendErrorMessage("Error", error)


            }

        }.launchIn(viewModelScope)


    }


    //use case #2
    private fun nextPage() {
        //prevent duplicate event due to recompose happening too quickly
        if ((movieListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            if (page.value > 1) {
                searchMovies.execute(
                    key = key, page = page.value, query =
                    query.value, connectivityManager.isNetworkAvailable.value
                ).onEach { dataState ->
                    loading.value = dataState.loading
                    dataState.data?.let { list ->
                        //filter by genre locally
                        val moviesInSelectedCategory = list.filter { movie ->
                            movie.genreIds?.contains(selectedCategory.value?.id ?: -1) == true
                        }
                        if (moviesInSelectedCategory.isEmpty()) {
                            appendMovies(list)
                        } else {
                            appendMovies(moviesInSelectedCategory)
                        }

                    }
                    dataState.error?.let { error ->
                        Log.e(TAG, "nextPage: $error")
                        dialogQueue.appendErrorMessage("Error", error)


                    }

                }.launchIn(viewModelScope)


            }
        }
    }

    /**
     * Append new movies to the current list of movies
     */
    private fun appendMovies(movies: List<Movie>) {
        val current = ArrayList(this.movies.value)
        current.addAll(movies)
        this.movies.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun onChangeMovieScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    /**
     * Called when a new search is executed.
     */
    private fun resetSearchState(isSearchLocally: Boolean = false) {
        movies.value = listOf()
        page.value = 1
        onChangeMovieScrollPosition(0)
        if (selectedCategory.value?.name != query.value && !isSearchLocally)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onSelectedCategoryChanged(category: Genre) {
        if (category.name == selectedCategory.value?.name) {
            clearSelectedCategory()
            onQueryChanged("")
            return
        }
        setSelectedCategory(category)
        onQueryChanged(category.name ?: "")
    }


    private fun setListScrollPosition(position: Int) {
        movieListScrollPosition = position
        savedStateHandle[STATE_KEY_LIST_POSITION] = position
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle[STATE_KEY_PAGE] = page
    }

    private fun setSelectedCategory(category: Genre?) {
        selectedCategory.value = category
        savedStateHandle[STATE_KEY_SELECTED_CATEGORY] = category
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle[STATE_KEY_QUERY] = query
    }


}
