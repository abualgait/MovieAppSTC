package com.stc.muhammadsayed.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stc.muhammadsayed.R
import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.presentation.compose.ColumnSpacer
import com.stc.muhammadsayed.presentation.theme.AppTheme
import com.stc.muhammadsayed.presentation.theme.Grey2
import com.stc.muhammadsayed.presentation.theme.TMDBColor


@ExperimentalComposeUiApi
@Composable
fun SearchFilterView(
    query: String,
    onQueryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    categories: List<Genre>,
    selectedCategory: Genre?,
    onSelectedCategoryChanged: (Genre) -> Unit,
) {
    var searchText by remember { mutableStateOf(TextFieldValue(query)) }
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
    ) {

        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                val keyboardController = LocalSoftwareKeyboardController.current
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp, horizontal = 10.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                onExecuteSearch()
                                keyboardController?.hide()
                            }),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontStyle = typography.titleMedium.fontStyle,
                                fontWeight = FontWeight.Normal,
                            ),

                            value = searchText,
                            maxLines = 1,
                            onValueChange = { newText ->
                                onQueryChanged(newText.text)
                                searchText = newText

                            },
                            decorationBox = { innerTextField ->
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    if (searchText.text.isEmpty()) {
                                        Text(
                                            color = Grey2,
                                            text = "Search TMDB",
                                            style = typography.titleMedium,
                                            maxLines = 1,
                                            fontWeight = FontWeight.Normal,
                                            modifier = Modifier
                                                .align(Alignment.CenterStart)
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )

                    }
                    if (searchText.text.isNotEmpty())
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                            contentDescription = "clearSearch",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .clickable {
                                    searchText = TextFieldValue("")
                                    onQueryChanged("")
                                    onExecuteSearch()
                                }
                        )
                }
            }
            Text(
                color = TMDBColor,
                text = "Watch New Movies",
                style = typography.displayMedium,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
            ColumnSpacer(value = 10)

            val scrollState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp),
                state = scrollState,
            ) {
                items(categories) {
                    MovieCategoryChip(
                        category = it,
                        isSelected = selectedCategory == it,
                        onSelectedCategoryChanged = { genre ->
                            onSelectedCategoryChanged(genre)
                        },
                        onExecuteSearch = {
                            onExecuteSearch()
                        },
                    )

                }
            }


        }
    }
}

@ExperimentalComposeUiApi
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchAppBarPreview() {
    AppTheme(
        darkTheme = true,
        isNetworkAvailable = true,
        displayProgressBar = false,
        dialogQueue = null

    ) {
        SearchFilterView(
            "",
            {},
            { },
            listOf(Genre(0, "Animation"), Genre(1, "Comedy")),
            Genre(0, "Animation")
        ) { }
    }
}
