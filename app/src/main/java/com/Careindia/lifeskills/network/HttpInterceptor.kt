package com.careindia.lifeskills.network

import android.util.Log
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.utils.PrefManager

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HttpInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request
        //Build new request
        val builder = chain.request().newBuilder()
            /*.header("Content-Type", "application/json")*/
            .addHeader(
                "Authorization","Bearer "+ PrefManager.getInstance(CareIndiaApplication.myApplication)!!.getStringValue(PrefManager.AUTH_TOKEN)!!.trim()
            )

        Log.d("auth_token",PrefManager.getInstance(CareIndiaApplication.myApplication)!!
            .getStringValue(PrefManager.AUTH_TOKEN)!!.trim())
        request = builder.build()
        val response = chain.proceed(request)
       /* if (response.code == API_SESSION_EXPIRED) {
            sendBroadcastUserSessionExpired()
        }*/
        return response
    }

   /* private fun sendBroadcastUserSessionExpired() {
        RestClient.client.dispatcher.cancelAll() // Cancel ALL running retrofit  request
        LocalBroadcastManager.getInstance(PlanItApp.appInstance)
            .sendBroadcastSync(Intent(ACTION_SESSION_EXPIRED))
    }*/
}