package com.raj.imageloading

import android.app.Application
import android.graphics.Bitmap

class ImageLoadingApplication : Application() {
    private val bitMapPool = arrayListOf<Bitmap>()
    override fun onCreate() {
        super.onCreate()
    }

    fun addToBitmapPool(bitmap: Bitmap) {
        bitMapPool.add(bitmap)//To reuse the bitmap storing in application object bitMapPool
    }

    fun getBitMapFromPool(width: Int, height: Int): Bitmap? {
        //Code to Check if bitmap of compatible size exist in pool

        if (bitMapPool.size > 0) {
            return bitMapPool.get(0)
        }
        return null
    }
}