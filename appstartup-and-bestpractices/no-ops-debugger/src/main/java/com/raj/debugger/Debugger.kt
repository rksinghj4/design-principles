package com.raj.debugger

object Debugger {

    fun log(tag: String, function: () -> String) {
        //Intentionally blank: because in release build we don't need to print the logs.
        //Release build size is reduce by 10 MB by excluding Printer library.
    }
}


/**
 * By using ops vs no-ops approach we can use two different app icons
 */