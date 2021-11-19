package com.careindia.lifeskills.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.careindia.lifeskills.BuildConfig
import com.careindia.lifeskills.network.RetrofitConstant.RETROFIT_SERVICE_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {
   private val client: OkHttpClient = getBuilder().build()

    private fun getBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            retryOnConnectionFailure(true)
            readTimeout(RETROFIT_SERVICE_TIMEOUT, TimeUnit.MILLISECONDS)
            connectTimeout(RETROFIT_SERVICE_TIMEOUT, TimeUnit.MILLISECONDS)
            followRedirects(true)
            followSslRedirects(true)
            addNetworkInterceptor(HttpInterceptor())
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                // set your desired log level
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
                // add logging as last interceptor
            }
        }
    }

    fun createApiHelper(): Api = Retrofit.Builder()
//        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(createGSon()))
        .build().create(Api::class.java)

    private fun createGSon(): Gson {
        return GsonBuilder().apply {
            setLenient()
        }.create()
    }
}