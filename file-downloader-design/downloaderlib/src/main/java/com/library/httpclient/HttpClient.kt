package com.library.httpclient

import com.library.internal.DownloadRequest

interface HttpClient {
    fun connect(request: DownloadRequest)
}