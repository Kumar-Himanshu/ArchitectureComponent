package com.testexample.componentExample.utils

import android.util.Log

import com.testexample.componentExample.BuildConfig

/**
 * The generic log class for application wide logging
 */
internal object Logs {
    val isLogsEnabled = BuildConfig.DEBUG

    fun v(tag: String, msg: String) {
        if (isLogsEnabled) {
            Log.v(tag, msg)
        }
    }

    fun v(tag: String, msg: String, e: Exception) {
        if (isLogsEnabled) {
            Log.v(tag, msg, e)
        }
    }

    fun v(tag: String, msg: String, e: OutOfMemoryError) {
        if (isLogsEnabled) {
            Log.v(tag, msg, e)
        }
    }

    fun reportException(e: Exception) {
        if (isLogsEnabled) {
            Log.e("Exception", e.toString(), e)
        }
    }
}
