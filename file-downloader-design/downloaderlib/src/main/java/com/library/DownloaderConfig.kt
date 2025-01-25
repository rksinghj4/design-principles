package com.library

import com.library.httpclient.DefaultHttpClient
import com.library.httpclient.HttpClient

data class DownloaderConfig(
    val httpClient: HttpClient = DefaultHttpClient(),
    val readTimeOut: Int = Constants.DEFAULT_READ_TIMEOUT_MILLS,
    val connectionTimeOut: Int = Constants.DEFAULT_CONNECTION_TIMEOUT_MILLS
)