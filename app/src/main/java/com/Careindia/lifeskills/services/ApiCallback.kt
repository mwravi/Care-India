package com.careindia.lifeskills.services

import com.careindia.lifeskills.services.response.LoginResponse
import com.careindia.lifeskills.services.response.MasterResponse
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

    @GET("/api/Training/GetTraining")
    fun prepostData(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

    @GET("/api/ProfileHH/GetProfileHHData")
    fun importHH(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

    @GET("/api/ProfileIndividual/GetProfileIndividualata")
    fun importIM(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

    @GET("/api/PsychometricData/GetPsychometricData")
    fun importPsycho(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

    @GET("/api/PDC/GetPDC")
    fun importprimary(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

    @GET("/api/CollectiveMeeting/GetCollectiveMeeting")
    fun importmeeting(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

    @GET("/api/CCMember/GetCCMemberData")
    fun importprofiledata(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>


    @GET("/api/PsychometricData/GetPsychometricData")
    fun getPsychoData(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<MasterResponse>

    @GET("/api/StatusETC/GetStatusETCData")
    fun getStatusData(
        @Query("UserID") username: String?
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/api/ProfileHH/Post_tblProfileHH")
    fun upload_HHdata(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/ProfileIndividual/Post_tblProfileIndividual")
    fun upload_individualdata(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/PsychometricData/Post_tblPsychometric")
    fun upload_psychometricdata(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/PDC/Post_tblPDC")
    fun upload_primarydata(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/CollectiveMeeting/Post_tblCollectiveMeeting")
    fun upload_meetdata(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/CCMember/PostCCMemberData")
    fun upload_profiledata(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/ProgressTracker/Post_tblCPTracker")
    fun upload_tblCPTrackerdata(@Body body: RequestBody?): Call<ResponseBody>

    @GET("/api/ProgressTracker/GetProgressTrackerData")
    fun importtblCPTrackerdata(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>


    @Headers("Content-Type: application/json")
    @POST("/api/PsychometricData/Post_tblPsychometric")
    fun upload_assessmentDetaildata(@Body body: RequestBody?): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/api/AADetails/PostAADeatislData")
    fun upload_assessmentdata(@Body body: RequestBody?): Call<ResponseBody>


    @Headers("Content-Type: application/json")
    @POST("/api/BeneficiaryTracker/PostBBData")
    fun upload_beneficiaryTrackerdata(@Body body: RequestBody?): Call<ResponseBody>

    @GET("/api/AADetails/GetCCMemberData")
    fun importAssessmentdata(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>

    @GET("/api/BeneficiaryTracker/GetBBTrackerData")
    fun importBeneficiaryTrackerdata(
        @Query("UserID") username: String?,
        @Query("ValidID2") password: String?
    ): Call<LoginResponse>


}