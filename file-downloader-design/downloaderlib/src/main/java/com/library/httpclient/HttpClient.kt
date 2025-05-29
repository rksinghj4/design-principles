package com.library.httpclient

import com.library.internal.DownloadRequest

/**
 * Always take interface in library. So that if newer version of OkHttp is released
 * then developer/apps who integrated my library should be able to provide their implements.
 */

interface HttpClient {
    fun connect(request: DownloadRequest)
}