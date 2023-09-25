package com.stc.muhammadsayed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.presentation.compose.MovieInfo
import com.stc.muhammadsayed.util.IMAGE_HEIGHT
import com.stc.muhammadsayed.util.loadPicture
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@Composable
fun MovieView(
    movie: Movie,
    onBackPressed: () -> Unit
) {
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {

                movie.posterImage.let { url ->
                    val image = loadPicture(url = url).value

                    image?.let { img ->

                        Image(
                            bitmap = img.asImageBitmap(),
                            contentDescription = "Movie Featured Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IMAGE_HEIGHT.dp),
                            contentScale = ContentScale.Crop
                        )

                    } ?: run {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IMAGE_HEIGHT.dp)
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    MovieInfo(movie = movie)
                }


            }
        }
        BackButton {
            onBackPressed()
        }
    }
}


@Composable
fun BackButton(
    onBackPressed: () -> Unit
) {

    Box(modifier = Modifier.padding(16.dp)) {

        IconButton(
            onClick = onBackPressed,
            modifier = Modifier.then(Modifier.size(24.dp))
        ) {
            Icon(
                Icons.Rounded.ArrowBack,
                "back button",
                tint = Color.White
            )
        }
    }
}


@ExperimentalCoroutinesApi
@Composable
@Preview
fun MovieViewPreview() {
    MovieView(
        Movie(
            poster_path = "https://image.tmdb.org/t/p/original/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
            title = "Blue Beetle",
            release_date = "2023",
            vote_average = 4.5
        )
    ) { }

}









