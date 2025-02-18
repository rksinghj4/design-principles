package com.raj.imageloading.bitmapcache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.collection.LruCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File

fun decodeImageFromPath() {
    val bitmapCache: LruCache<String, Bitmap>
    val programMaxSizeInKB = Runtime.getRuntime().maxMemory() / 1024 //KB
    val cacheSize = programMaxSizeInKB / 8

    bitmapCache = object : LruCache<String, Bitmap>(cacheSize.toInt()) {
        override fun sizeOf(key: String, bitMap: Bitmap): Int {
            //return super.sizeOf(key, value)
            //Log.d("BitmapCache", "Bitmap cache in KB ${bitmap.byteCount / 1024}")
            return bitMap.byteCount / 1024
        }
    }

    runBlocking {
        try {
            CoroutineScope(Dispatchers.IO).async {
                val filePath =
                    File("/Users/raj.singh/selfimprovement/design-principles/image-loading-issues/app/src/main/res/drawable/main.png")
                if (filePath.exists()) {
                    val mainBitMap =
                        BitmapFactory.decodeFile(filePath.absolutePath)
                    bitmapCache.put(
                        "main.png",
                        mainBitMap
                    )
                    //println("filePath.absolutePath ${filePath.absolutePath}")
                    println("mainBitMap.byteCount - ${mainBitMap.byteCount} at filePath.absolutePath ${filePath.absolutePath}")
                    Log.d(
                        "decodeImageFromPath",
                        "mainBitMap.byteCount - ${mainBitMap.byteCount} at filePath.absolutePath ${filePath.absolutePath}"
                    )
                }

            }.await()
        } catch (e: Exception) {
            //Log.d("decodeImageFromPath Exception", e.toString())
            e.printStackTrace()
        }
    }

}