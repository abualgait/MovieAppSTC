package com.stc.muhammadsayed.presentation.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.stc.muhammadsayed.domain.model.Movie
import com.stc.muhammadsayed.presentation.components.RowSpacer
import com.stc.muhammadsayed.presentation.theme.Blue700
import com.stc.muhammadsayed.util.genreToCommaSeparatedString
import com.stc.muhammadsayed.util.loadPicture

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
            } ?: run {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(100.dp)
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
    val context = LocalContext.current
    val divider = "\n"
    val annotatedString = buildAnnotatedString {

        val styleTitle = SpanStyle(
            fontStyle = typography.bodyMedium.fontStyle,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
        )

        val styleDes = SpanStyle(
            color = MaterialTheme.colorScheme.onBackground,
        )

        withStyle(style = styleTitle) {

            append("Homepage: ")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline,
                color = Blue700,
            )
        ) {
            addStringAnnotation(
                "URL",
                movie.homepage ?: "",
                "Homepage: ".length,
                "Homepage: ${movie.homepage}".length
            )
            append(movie.homepage)
        }
        append(divider)

        withStyle(style = styleTitle) {
            append("Language: ")
        }
        withStyle(style = styleDes) {
            append(movie.commaSeparatedLanguages)
        }

        append(divider)

        withStyle(style = styleTitle) {
            append("Status: ")
        }
        withStyle(style = styleDes) {
            append(movie.status)
        }
        append(divider)

        withStyle(style = styleTitle) {
            append("Runtime: ")
        }
        withStyle(style = styleDes) {
            append(movie.runTime)
        }
        append(divider)

        withStyle(style = styleTitle) {
            append("Budget: ")
        }
        withStyle(styleDes) {
            append(movie.movieBudget)
        }

        append(divider)
        withStyle(style = styleTitle) {
            append("Revenue: ")
        }
        withStyle(styleDes) {
            append(movie.movieRevenue)
        }

    }

    ClickableText(
        onClick = {
            annotatedString.getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    context.openUrl(stringAnnotation.item)
                }
        },
        text = annotatedString,
        modifier = modifier.alpha(0.5f)
    )

}

fun Context.openUrl(uri: String) {
    var url = uri
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        url = "http://$url"
    }

    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(browserIntent)

}


@Composable
fun ColumnSpacer(value: Int) = Spacer(modifier = Modifier.height(value.dp))

