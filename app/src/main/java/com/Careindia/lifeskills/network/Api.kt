package com.careindia.lifeskills.network

import com.careindia.lifeskills.constants.ServerConstant
import com.careindia.lifeskills.views.loginscreen.LoginRequest

import com.careindia.lifeskills.views.loginscreen.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST(ServerConstant.LOGIN)
    suspend fun getLoginApi(@Body apiRequest: LoginRequest): RegistrationResponse
}