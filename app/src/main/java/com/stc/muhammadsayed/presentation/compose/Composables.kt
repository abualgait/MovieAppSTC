package com.stc.muhammadsayed.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.presentation.components.RowSpacer
import com.stc.muhammadsayed.util.genreToCommaSeparatedString
import com.stc.muhammadsayed.util.loadPicture
import java.util.Locale

@Composable
fun MovieInfo(movie: Movie) {
    ColumnSpacer(16)

    Row {
        movie.posterImage.let { url ->
            val image = loadPicture(url = url).value

            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Movie Featured Image",
                    modifier = Modifier
                        .width(60.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Fit
                )
            }

        }
        RowSpacer(value = 8)
        Column {
            movie.original_title?.let {
                Text(
                    text = "$it (${movie.date})",
                    fontWeight = FontWeight.Bold,
                    style = typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            movie.genres?.let {
                Text(
                    text = it.genreToCommaSeparatedString(),
                    fontWeight = FontWeight.Normal,
                    style = typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }

    ColumnSpacer(8)

    movie.overview?.let {
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = it,
            style = typography.bodyMedium,
            maxLines = 15,
        )
    }

    Spacer(Modifier.height(20.dp))
    MovieMetadata(movie = movie)
    Spacer(Modifier.height(16.dp))


}

@Composable
fun MovieMetadata(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    val divider = "  •  "
    val text = buildAnnotatedString {
        append(movie.rating)
        append(divider)
        append(movie.date)
        append(divider)
        append(movie.runTime)
        append(divider)
        movie.original_language?.language()?.let { append(it) }
    }

    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = text,
        style = typography.bodyMedium,
        modifier = modifier.alpha(0.5f)
    )

}

fun String.language(): String {
    val loc = Locale(this)
    return loc.getDisplayLanguage(loc)
}


@Composable
fun ColumnSpacer(value: Int) = Spacer(modifier = Modifier.height(value.dp))

