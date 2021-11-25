package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstPanchayatWardDao
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity

class MstPanchayatWardRepository {
    var mstPanchayatWardDao: MstPanchayatWardDao?=null
    constructor(application: Application) {
        mstPanchayatWardDao = CareIndiaApplication.database?.mstPanchayatWardDao()
    }

   fun getMstPanchayat_Ward(StateCode:Int,DistrictCode:Int,ZoneCode:Int): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardDao!!.getMstPanchayat_Ward(StateCode,DistrictCode,ZoneCode)
    }







}