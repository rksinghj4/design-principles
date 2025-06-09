package com.raj.debugger
//Assume size of this library = 1 KB
object Debugger {
    fun log(tag: String, function: () -> String) {
        Printer.print(tag, function) //+ 10 MB
    }
}