package com.stc.muhammadsayed.presentation


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.stc.muhammadsayed.presentation.ui.movie_list.MovieListScreen
import com.stc.muhammadsayed.presentation.ui.movie_list.MovieListViewModel
import com.stc.muhammadsayed.presentation.util.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager


    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: MovieListViewModel = hiltViewModel()
            MovieListScreen(
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                onNavigateToMovieDetailScreen = { /*GOTO Movie Detail*/ },
                viewModel = viewModel
            )

        }

    }
}