package com.careindia.lifeskills.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.entity.MstDistrictEntity

class MstDistrictRepository {
    var mstDistrictDao: MstDistrictDao? = null

    constructor(application: Application) {
        mstDistrictDao = CareIndiaApplication.database?.mstDistrictDao()
    }

    fun getMstDistrictLive(StateCode:Int,DistrictIn:List<String>): LiveData<List<MstDistrictEntity>> {
        return mstDistrictDao!!.getMstDistrict(StateCode,DistrictIn)
    }

    fun getMstDistrict(StateCode:Int,DistrictIn:List<String>): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDist(StateCode,DistrictIn)
    }

    fun getMstDistrict(StateCode:Int): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDist(StateCode)
    }
    fun getMstDistrict(): List<MstDistrictEntity> {
        return mstDistrictDao!!.getMstDistrict()
    }

    fun getMstDistrict(StateCode:Int,DistrictCode:Int): String {
        return mstDistrictDao!!.getMstDistrict(StateCode,DistrictCode)
    }

}