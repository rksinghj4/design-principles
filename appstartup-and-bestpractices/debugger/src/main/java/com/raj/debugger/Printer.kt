package com.raj.debugger

import android.util.Log
//Assume: Size of this library 10 MB
object Printer {
    fun print(tag: String, lambdaFun: ()->String) {
        Log.d(tag, lambdaFun.invoke())
    }
}