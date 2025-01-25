package com.library.httpclient

import com.library.Constants
import com.library.internal.DownloadRequest
import java.net.URL
import java.util.Locale

class DefaultHttpClient : HttpClient {
    override fun connect(request: DownloadRequest) {

       /* val range: String = String.format(
            Locale.getDefault(),
            "bytes=%d-",
            request.downloadedBytes
        )

        val connection = URL(request.url).openConnection()
        connection.apply {
            readTimeout = request.readTimeOut
            connectTimeout = request.connectionTimeOut
            addRequestProperty(Constants.RANGE, range)
        }*/
    }
}