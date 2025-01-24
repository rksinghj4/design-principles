package com.library.internal

class DownloadRequestQueue(private val dispatcher: DownloadDispatcher) {

    private val idRequestMap: HashMap<Int, DownloadRequest> = hashMapOf()

    fun enqueue(request: DownloadRequest) {
        idRequestMap[request.downloadId] = request
        dispatcher.enqueue(request)
    }

    fun pause(id: Int) {
        idRequestMap[id]?.let {
            dispatcher.cancel(it)//For pause just cancel the job
        }
    }

    fun resume(id: Int) {
        idRequestMap[id]?.let {
            dispatcher.enqueue(it)//For resume, enqueue it again
        }
    }

    fun cancel(id: Int) {
        idRequestMap[id]?.let {
            dispatcher.cancel(it)//For pause and cancel just cancel the job
        }
        idRequestMap.remove(id)//Remove DownloadRequest from hashmap
    }

    fun cancel(tag: String) {
        val requestsWithTag = idRequestMap.values.filter {
            it.tag == tag
        }

        for (req in requestsWithTag) {
            cancel(req.downloadId)
        }
    }

    fun cancelAll() {
        idRequestMap.clear()
        dispatcher.cancelAll()
    }
}


