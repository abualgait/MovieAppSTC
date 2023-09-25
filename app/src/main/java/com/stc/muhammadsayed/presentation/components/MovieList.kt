@file:OptIn(ExperimentalComposeUiApi::class)

package com.stc.muhammadsayed.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.util.PAGE_SIZE
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@Composable
fun MovieList(
    loading: Boolean,
    movies: List<Movie>,
    onChangeMovieScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: () -> Unit,
    onNavigationToMovieDetailScreen: (Movie) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {


        if (movies.isEmpty() && !loading) {
            NothingHere()
        } else {
            LazyVerticalGrid(
                GridCells.Fixed(2)
            ) {
                itemsIndexed(
                    items = movies
                ) { index, movie ->
                    onChangeMovieScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage()
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(8.dp)
                    ) {
                        MovieCard(
                            movie = movie,
                            onClick = {
                                onNavigationToMovieDetailScreen(movie)
                            }
                        )
                    }

                }
            }
        }
    }
}
















