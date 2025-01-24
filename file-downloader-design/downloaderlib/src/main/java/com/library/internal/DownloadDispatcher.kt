package com.library.internal

import com.library.httpclient.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DownloadDispatcher(
    private val httpClient: HttpClient,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
) {

    fun enqueue(req: DownloadRequest): Int {
        val job = scope.launch {

        }

        req.job = job
        return req.downloadId
    }

    private suspend fun execute(req: DownloadRequest, task: DownloadTask) {
        task.run(
            onStart = {
                //This line run on IO thread
                executeOnMainThread { req.onStart() }
            },
            onProgress = {
                executeOnMainThread { req.onProgress() }

            },
            onPause = {
                executeOnMainThread { req.onPause() }

            },
            onCompleted = {
                executeOnMainThread { req.onCompleted() }

            },
            onError = {
                executeOnMainThread { req.onError(it) }
            }
        )
    }

    private fun executeOnMainThread(block: () -> Unit) {
        //here - IO thread
        //this coroutine with switch to main thread
        scope.launch {
            //new coroutine launched on main thread
            block()
        }
    }

    fun cancel(req: DownloadRequest) {
        req.job.cancel()
    }

    fun cancelAll() {
        /**
         * If app, which uses our library switch between activity then
         * new Activity will not be able to enqueue the new request in same scope.
         * So it better to cancel each req.job.cancel() using for loop
         */
        scope.cancel()
    }
}