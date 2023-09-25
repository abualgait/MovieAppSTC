package com.stc.muhammadsayed.presentation.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.stc.muhammadsayed.presentation.components.CircularIndeterminateProgressBar
import com.stc.muhammadsayed.presentation.components.ConnectivityMonitor
import com.stc.muhammadsayed.presentation.components.GenericDialog
import com.stc.muhammadsayed.presentation.components.GenericDialogInfo
import java.util.Queue


private val LightThemeColors = lightColorScheme(
    primary = TMDBColor,
    primaryContainer = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryContainer = Teal300,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2
)

private val DarkThemeColors = darkColorScheme(
    primary = TMDBColor,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White
)


@ExperimentalComposeUiApi
@Composable
fun AppTheme(
    darkTheme: Boolean,
    isNetworkAvailable: Boolean,
    displayProgressBar: Boolean,
    dialogQueue: Queue<GenericDialogInfo>? = null,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (!darkTheme) Grey1 else Color.Black)
        ) {
            Column {
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }

            CircularIndeterminateProgressBar(isDisplayed = displayProgressBar)
            ProcessDialogQueue(dialogQueue = dialogQueue)
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>?,
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}





























