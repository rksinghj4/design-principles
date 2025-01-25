package com.filedownloader.design

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.filedownloader.design.ui.theme.FileDownloaderDesignTheme
import com.library.Downloader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            FileDownloaderDesignTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val progress = rememberSaveable {
                        mutableIntStateOf(0)
                    }

                    val downloadStatus = rememberSaveable {
                        mutableStateOf("")
                    }

                    MainScreen(viewModel.progress, viewModel.downloadStatus) {
                        when (it) {
                            Status.START -> {
                                viewModel.setShouldStartDownload(shouldStartDownload = true)
                                viewModel.startDownload()
                            }

                            Status.PAUSE -> {
                                viewModel.setShouldStartDownload(shouldStartDownload = false)
                                viewModel.pauseDownload()
                            }

                            Status.RESUME -> {
                                viewModel.setShouldStartDownload(shouldStartDownload = true)
                                viewModel.resumeDownload()
                            }

                            Status.CANCEL -> {
                                viewModel.setShouldStartDownload(shouldStartDownload = false)
                                viewModel.cancelDownload()
                            }

                            Status.CANCEL_ALL -> {
                                viewModel.setShouldStartDownload(shouldStartDownload = false)
                                viewModel.cancelAllDownload()
                            }

                            else -> {

                            }
                        }

                    }
                }
            }
        }
    }
}



