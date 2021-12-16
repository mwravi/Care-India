package com.mamta.sabal.callback

import com.mamta.sabal.service.response.LoginResponse
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiCallback {




    @GET("/api/MastersData/GetMasterData")
    fun login(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

  /*  @POST("/api/ProfileHH/Post_tblProfileHH")
    fun upload_data(@Header("Content-Type") contenttype: String,
        @Body tblProfileHH: RequestBody?
    ): Call<ResponseBody>*/


   /* @Headers("Content-Type: application/json")
    @POST("/api/ProfileHH/Post_tblProfileHH")
    fun upload_data(@Body body: String?): Call<ResponseBody>*/

    @Headers("Content-Type: application/json")
    @POST("/api/ProfileHH/Post_tblProfileHH")
    fun upload_data(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/ProfileIndividual/Post_tblProfileIndividual")
    fun upload_individualdata(@Body body: RequestBody?): Call<ResponseBody>




}