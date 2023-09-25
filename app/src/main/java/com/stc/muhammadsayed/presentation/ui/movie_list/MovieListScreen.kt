package com.stc.muhammadsayed.presentation.ui.movie_list

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.presentation.components.MovieList
import com.stc.muhammadsayed.presentation.components.SearchFilterView
import com.stc.muhammadsayed.presentation.theme.AppTheme
import com.stc.muhammadsayed.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MovieListScreen(
    isNetworkAvailable: Boolean,
    onNavigateToMovieDetailScreen: (Movie) -> Unit,
    viewModel: MovieListViewModel
) {

    Log.d(TAG, "MovieListScreen: $viewModel")
    val movies = viewModel.movies.value
    val genres = viewModel.genres.value
    val query = viewModel.query.value
    val selectedCategory = viewModel.selectedCategory.value
    val loading = viewModel.loading.value
    val page = viewModel.page.value
    val dialogQueue = viewModel.dialogQueue

    AppTheme(
        darkTheme = true,
        isNetworkAvailable = isNetworkAvailable,
        displayProgressBar = loading,
        dialogQueue = dialogQueue.queue.value
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            SearchFilterView(
                query = query,
                onQueryChanged = viewModel::onQueryChanged,
                onExecuteSearch = { viewModel.onTriggerEvent(MovieListEvent.NewSearchEvent) },
                categories = genres,
                selectedCategory = selectedCategory,
                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged

            )
            MovieList(
                loading = loading,
                movies = movies,
                onChangeMovieScrollPosition = viewModel::onChangeMovieScrollPosition,
                page = page,
                onNextPage = { viewModel.onTriggerEvent(MovieListEvent.NextPageEvent) },
                onNavigationToMovieDetailScreen = onNavigateToMovieDetailScreen

            )
        }


    }

}
