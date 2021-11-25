package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.entity.MstDistrictEntity

class MstDistrictRepository {
    var mstDistrictDao: MstDistrictDao?=null
    constructor(application: Application) {
        mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()
    }

   fun getMstDistrict(StateCode:Int): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDistrict(StateCode)
    }







}