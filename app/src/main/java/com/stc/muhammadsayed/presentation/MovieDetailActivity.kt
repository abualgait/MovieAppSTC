package com.stc.muhammadsayed.presentation


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.stc.muhammadsayed.presentation.ui.movie.MovieDetailScreen
import com.stc.muhammadsayed.presentation.ui.movie.MovieDetailViewModel
import com.stc.muhammadsayed.presentation.util.ConnectivityManager
import com.stc.muhammadsayed.util.STATE_KEY_MOVIE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        fun startActivity(mActivity: Activity, movieId: Int? = null) =
            Intent(mActivity, MovieDetailActivity::class.java).apply {
                putExtra(STATE_KEY_MOVIE, movieId)
            }.also {
                mActivity.startActivity(it)
            }
    }

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

            val viewModel: MovieDetailViewModel = hiltViewModel()
            viewModel.movieId = intent.getIntExtra(STATE_KEY_MOVIE, -1)
            MovieDetailScreen(
                true,
                isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                movieId = viewModel.movieId,
                viewModel = viewModel
            ) {
                finish()
            }

        }

    }
}