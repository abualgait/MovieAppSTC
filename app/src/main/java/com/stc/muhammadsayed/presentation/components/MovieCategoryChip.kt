package com.stc.muhammadsayed.presentation.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stc.muhammadsayed.domain.model.Genre
import com.stc.muhammadsayed.presentation.theme.TMDBColor

@Composable
fun MovieCategoryChip(
    category: Genre,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (Genre) -> Unit,
    onExecuteSearch: () -> Unit,
) {
    Surface(
        border = BorderStroke(1.dp, color = TMDBColor),
        modifier = Modifier.padding(end = 8.dp),
        shape = MaterialTheme.shapes.extraLarge,
        color = if (isSelected) TMDBColor else MaterialTheme.colorScheme.background
    ) {
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category)
                    onExecuteSearch()
                }
            )
        ) {
            Text(
                text = category.name ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) Color.Black else Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}










