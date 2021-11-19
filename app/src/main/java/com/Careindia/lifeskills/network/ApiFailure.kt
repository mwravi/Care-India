package com.mymuhammadiyah.app.network

import android.util.MalformedJsonException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ApiFailure {
    companion object
    {
          fun getCustomErrorMessage(error: Throwable): String {
            return when (error) {
                is SocketTimeoutException -> {
                    "Oops! We couldn’t capture your request in time. Please try again."
                }
                is MalformedJsonException -> {
                    "Oops! We hit an error. Try again later."
                }
                is IOException -> {
                    "Oh! You are not connected to a wifi or cellular data network. Please connect and try again"
                }
                is HttpException -> {
                    error.response()!!.message()
                }
                else -> {
                    "Something went wrong"
                }
            }
        }
    }
}