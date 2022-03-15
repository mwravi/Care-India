package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstPanchayatWardDao
import com.careindia.lifeskills.entity.MstPanchayat_WardEntity

class MstPanchayatWardRepository {
    var mstPanchayatWardDao: MstPanchayatWardDao? = null

    constructor(application: Application) {
        mstPanchayatWardDao = CareIndiaApplication.database?.mstPanchayatWardDao()
    }

    fun getMstPanchayat_Ward(
        StateCode: Int,
        DistrictCode: Int,
        ZoneCode: Int
    ): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardDao!!.getMstPanchayat_Ward(StateCode, DistrictCode, ZoneCode)
    }


    fun getMstWard(ZoneCode: Int,PanchayatIn: List<String>): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardDao!!.getMstWard(ZoneCode,PanchayatIn)
    }

    fun getMstWard(ZoneCode: Int): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardDao!!.getMstWard(ZoneCode)
    }

    fun getMstPanchayat(disCode: Int,WardIn: List<String>): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardDao!!.getMstPanchayat(disCode,WardIn)
    }

    fun getMstPanchayat(disCode: Int): List<MstPanchayat_WardEntity> {
        return mstPanchayatWardDao!!.getMstPanchayat(disCode)
    }

    fun getMstWard(ZoneCode: Int,WardCode:Int): String {
        return mstPanchayatWardDao!!.getMstWard(ZoneCode,WardCode)
    }
    fun getMstPanchayat(disCode: Int,WardCode:Int): String {
        return mstPanchayatWardDao!!.getMstPanchayat(disCode,WardCode)
    }
}