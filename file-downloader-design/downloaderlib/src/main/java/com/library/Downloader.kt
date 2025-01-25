package com.library

import com.library.httpclient.DefaultHttpClient
import com.library.internal.DownloadDispatcher
import com.library.internal.DownloadRequest
import com.library.internal.DownloadRequestQueue

//It will act like facade to apps, who wants to use it.
class Downloader private constructor(
    private val config: DownloaderConfig,// This is private, not exposed to client
) {
    //Need to inject
    private val reqQueue: DownloadRequestQueue =
        DownloadRequestQueue(DownloadDispatcher(config.httpClient))

    companion object {
        //Factory abstract the object initialization process
        fun create(
            config: DownloaderConfig = DownloaderConfig(),
        ): Downloader {
            return Downloader(config)
        }
    }

    fun newRequestBuilder(
        url: String,
        dirPath: String,
        fileName: String
    ): DownloadRequest.Builder {
        return DownloadRequest.Builder(url = url, dirPath = dirPath, fileName = fileName)
            .readTimeOut(config.readTimeOut)
            .connectionTimeOut(config.connectionTimeOut)
    }

    fun enqueue(
        req: DownloadRequest,
        onStart: () -> Unit,
        onProgress: (value: Int) -> Unit = { _ -> },
        onPause: () -> Unit = {},
        onCancel: () -> Unit = {},
        onCompleted: () -> Unit = {},
        onError: (error: String) -> Unit = { _ -> }
    ): Int {
        req.onStart = onStart
        req.onProgress = onProgress
        req.onPause = onPause
        req.onCancel = onCancel
        req.onCompleted = onCompleted
        req.onError = onError
        return reqQueue.enqueue(req)
    }

    fun pause(id: Int) {
        reqQueue.pause(id)
    }

    fun resume(id: Int) {
        reqQueue.resume(id)
    }

    fun cancel(id: Int) {
        reqQueue.cancel(id)
    }

    fun cancel(tag: String) {
        reqQueue.cancel(tag)
    }

    fun cancelAll() {
        reqQueue.cancelAll()
    }
}