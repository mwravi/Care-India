package com.careindia.lifeskills.views.loginscreen

import com.careindia.lifeskills.ui.base.BaseRepository


class LoginRepository : BaseRepository() {

    suspend fun getLoginApi(apiRequest: LoginRequest)=apiHelper.getLoginApi(apiRequest)



}