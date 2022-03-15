package com.careindia.lifeskills.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstDistrictDao
import com.careindia.lifeskills.dao.MstZoneDao
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.entity.MstZoneEntity

class MstZoneRepository {
    var mstZoneDao: MstZoneDao?=null
    constructor(application: Application) {
        mstZoneDao = CareIndiaApplication.database?.mstZoneDao()
    }


    fun getMstZoneLive(DistrictCode:Int): LiveData<List<MstZoneEntity>> {
        return mstZoneDao!!.getMstZoneLive(DistrictCode)
    }

    fun getMstZone(DistrictCode:Int,ZoneIn: List<String>): List<MstZoneEntity> {
        return mstZoneDao!!.getMstZone(DistrictCode,ZoneIn)
    }

    fun getMstZone(DistrictCode:Int): List<MstZoneEntity> {
        return mstZoneDao!!.getMstZone(DistrictCode)
    }
    fun getMstZone(DistrictCode:Int,ZoneCode:Int): String{
        return mstZoneDao!!.getMstZone(DistrictCode,ZoneCode)
    }

}