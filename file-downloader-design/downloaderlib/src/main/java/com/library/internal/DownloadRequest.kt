package com.library.internal

import com.library.utils.getUniqueId
import kotlinx.coroutines.Job

class DownloadRequest private constructor(
    internal val url: String,
    internal val dirPath: String,
    internal val fileName: String,
    internal val tag: String?,
    internal val downloadId: Int,
    internal val readTimeOut: Int,
    internal val connectionTimeOut: Int
) {

    internal var totalBytes: Long = 0
    internal var downloadedBytes: Long = 0
    internal lateinit var job: Job
    internal lateinit var onStart: () -> Unit
    internal lateinit var onProgress: (value: Int) -> Unit
    internal lateinit var onPause: () -> Unit//Need answer: why resume functionality is not in Request?
    internal lateinit var onResume: () -> Unit
    internal lateinit var onCancel: () -> Unit
    internal lateinit var onCompleted: () -> Unit
    internal lateinit var onCancelAll: () -> Unit
    internal lateinit var onError: (error: String) -> Unit

    //There 3 parameters are mandatory to take from user
    data class Builder(
        private val url: String,
        private val dirPath: String,
        private val fileName: String
    ) {

        private var tag: String? = null
        private var readTimeOut: Int = 0
        private var connectionTimeOut: Int = 0

        fun tag(tag: String) = apply {
            this.tag = tag
        }

        fun readTimeOut(readTimeOut: Int) = apply {
            this.readTimeOut = readTimeOut
        }

        fun connectionTimeOut(connectionTimeOut: Int) = apply {
            this.connectionTimeOut = connectionTimeOut
        }

        /**
         * Alternative to write above function is given below
         */
        /*fun connectionTimeOut1(connectionTimeOut: Int): Builder {
            this.connectionTimeOut = connectionTimeOut
            return this
        }*/

        fun build(): DownloadRequest {
            return DownloadRequest(
                url = url,
                dirPath = dirPath,
                fileName = fileName,
                tag = tag,
                downloadId = getUniqueId(url = url, dirPath = dirPath, fileName = fileName),
                readTimeOut = readTimeOut,
                connectionTimeOut = connectionTimeOut
            )
        }
    }
}

