package com.stc.muhammadsayed.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.presentation.compose.ColumnSpacer
import com.stc.muhammadsayed.presentation.theme.AppTheme
import com.stc.muhammadsayed.util.loadPicture

@Composable
fun MovieCard(movie: Movie, isFirstCard: Boolean = false, onClick: () -> Unit) {
    RowSpacer(value = if (isFirstCard) 16 else 4)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier,
        ) {
            val image = loadPicture(url = movie.imageUrl).value
           image?.let {
                Image(
                    bitmap = image.asImageBitmap(),
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp),
                    contentScale = ContentScale.Crop,
                )
            }?: run {
               Box(modifier = Modifier
                   .fillMaxWidth()
                   .height(225.dp))
           }

        }
        ColumnSpacer(8)
        val padding = Modifier.padding(horizontal = 8.dp)
        movie.title?.let {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = it,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                minLines = 2,
                fontWeight = FontWeight.Bold,
                modifier = padding
            )
        }

        ColumnSpacer(4)
        Text(
            text = movie.year,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelSmall,
            modifier = padding
        )
        ColumnSpacer(4)
        // Rating
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = padding
        ) {
            // Star

            Icon(
                Icons.Rounded.Star,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(12.dp),
                contentDescription = "Star Ratings",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

            // Rating
            Text(
                text = movie.rating,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(top = 2.dp)
            )

        }


        ColumnSpacer(8)

    }
    RowSpacer(value = 4)
}

@Composable
fun RowSpacer(value: Int) = Spacer(modifier = Modifier.width(value.dp))


@ExperimentalComposeUiApi
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MovieCardPreview() {
    AppTheme(
        darkTheme = true,
        isNetworkAvailable = true,
        displayProgressBar = false,
        dialogQueue = null

    ) {
        MovieCard(
            Movie(
                poster_path = "https://image.tmdb.org/t/p/original/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
                title = "Blue Beetle",
                release_date = "2023",
                vote_average = 4.5
            ), false, {}
        )
    }
}


