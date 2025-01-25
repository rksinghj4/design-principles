package com.library.internal

import com.library.httpclient.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class DownloadTask(
    private val req: DownloadRequest,
    private val httpClient: HttpClient
) {

    suspend fun run(
        onStart: () -> Unit,
        onProgress: (value: Int) -> Unit = { _ -> },
        onPause: () -> Unit = {},
        onCancel: () -> Unit = {},
        onCompleted: () -> Unit = {},
        onError: (error: String) -> Unit = { _ -> }
    ) {
        //Run on background thread
        withContext(Dispatchers.IO) {
            //Dummy code for downloading the file
            onStart()

            //use of httpclient
            httpClient.connect(req)

            // simulate read data from internet
            for (i in 1..10) {
                delay(500)
                onProgress(i)
            }

            onCompleted()
        }

    }

}