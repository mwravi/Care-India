package com.careindia.lifeskills.services

import com.careindia.lifeskills.utils.extentions.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClientConnection {

    fun createApiInterface(): ApiCallback? {
        apiInterface = null
        if (apiInterface == null) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpBuilder = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)

            httpBuilder.addInterceptor(loggingInterceptor)

            val builder = Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpBuilder.build())

            val retrofit = builder.build()
            apiInterface = retrofit.create(ApiCallback::class.java)

        }
        return apiInterface
    }

    companion object {

        val Base_URL = Config().BASE_URL

        private var apiClientConnection: ApiClientConnection? = null
        private var apiInterface: ApiCallback? = null

        val instance: ApiClientConnection
            get() {
                apiClientConnection = null
                if (apiClientConnection == null) {
                    apiClientConnection = ApiClientConnection()
                }
                return apiClientConnection!!
            }
    }


}