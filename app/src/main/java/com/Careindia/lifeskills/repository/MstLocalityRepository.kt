package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstLocalityDao
import com.careindia.lifeskills.entity.MstLocalityEntity

class MstLocalityRepository {
    var mstLocalityDao: MstLocalityDao?=null
    constructor(application: Application) {
        mstLocalityDao = CareIndiaApplication.database?.mstLocalityDao()
    }

   fun getMstLocality(StateCode:Int,DistrictCode:Int,ZoneCode:Int,PWCode:Int): List<MstLocalityEntity> {
        return mstLocalityDao!!.getMstLocality(StateCode,DistrictCode,ZoneCode,PWCode)
    }







}