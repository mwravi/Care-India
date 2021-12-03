package com.mamta.sabal.callback

import com.mamta.sabal.service.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiCallback {




    @GET("/api/MastersData/GetMasterData")
    fun login(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>


}