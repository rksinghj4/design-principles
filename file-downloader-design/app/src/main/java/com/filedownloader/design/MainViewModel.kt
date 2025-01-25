package com.filedownloader.design

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.library.Downloader
import kotlin.properties.Delegates

class MainViewModel(internal val downloader: Downloader = Downloader.create()) : ViewModel() {
    private val request = downloader.newRequestBuilder(
        url = "any_url",
        dirPath = "where_to_save",
        fileName = "file_name"
    ).build()

    private var _downloadStatus: MutableState<String> = mutableStateOf("")
    internal val downloadStatus: State<String> = _downloadStatus

    private var _shouldStartDownload: MutableState<Boolean> = mutableStateOf(false)
    val shouldStartDownload: State<Boolean> = _shouldStartDownload

    private var _progress = mutableIntStateOf(0)
    internal val progress = _progress.asIntState()

    var requestId = 0

    var requestCount: Int by Delegates.vetoable(0) { property, oldValue, newValue ->
        Log.d("Requested download count", "$newValue")
        if (newValue < 2 && shouldStartDownload.value) {
            Log.d("Fired Request count", "$newValue")
            requestId = downloader.enqueue(
                req = request,
                onStart = {
                    _downloadStatus.value = "Started"
                },
                onProgress = { percentage ->
                    _progress.intValue = percentage
                },
                onPause = {
                    _downloadStatus.value = "Paused"
                },
                onCancel = {
                    _downloadStatus.value = "Canceled"
                },
                onCompleted = {
                    _downloadStatus.value = "Completed"
                },
                onError = { error ->
                    _downloadStatus.value = error
                }
            )
            return@vetoable true
        } else {
            return@vetoable false
        }
    }


    fun setShouldStartDownload(shouldStartDownload: Boolean) {
        _shouldStartDownload.value = shouldStartDownload
    }

    fun startDownload() {
        requestCount++
    }

    fun pauseDownload() {
        downloader.pause(requestId)
    }

    fun resumeDownload() {
        downloader.resume(requestId)
    }

    fun cancelDownload() {
        //requestCount--
        downloader.cancel(requestId)
    }

    fun cancelAllDownload() {
        //requestCount = 0
        downloader.cancelAll()
    }

}
