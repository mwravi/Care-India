package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstUserDao
import com.careindia.lifeskills.entity.MstUserEntity

class MstUserRepository {
    var mstUserDao: MstUserDao?=null
    constructor(application: Application) {
        mstUserDao = CareIndiaApplication.database?.mstUserDao()
    }

   fun getMstUser(): List<MstUserEntity> {
        return mstUserDao!!.getMstUser()
    }

    fun getusersCount(): Int {
        return mstUserDao!!.getusersCount()
    }





}