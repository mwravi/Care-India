package com.careindia.lifeskills.utils

import android.util.Log

object LogUtils {
    private const val DEBUG = true/*BuildConfig.DEBUG*/
    @JvmStatic
    fun errorLog(TAG: String, message: String) {
        if (DEBUG) {
            Log.e(TAG, message)
        }
    }
    @JvmStatic
    fun debugLog(TAG: String, message: String) {
        if (DEBUG) {
            Log.d(TAG, message)
        }
    }
    @JvmStatic
    fun warnLog(TAG: String, message: String) {
        if (DEBUG) {
            Log.w(TAG, message)
        }
    }
    @JvmStatic
    fun verboseLog(TAG: String, message: String) {
        if (DEBUG) {
            Log.v(TAG, message)
        }
    }
    @JvmStatic
    fun infoLog(TAG: String, message: String) {
        if (DEBUG) {
            Log.i(TAG, message)
        }
    }
}