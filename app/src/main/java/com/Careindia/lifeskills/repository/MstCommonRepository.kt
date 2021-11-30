package com.careindia.lifeskills.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.MstCommonEntity

class MstCommonRepository {
    var mstCommonDao: MstCommonDao?=null
    constructor(application: Application) {
        mstCommonDao = CareIndiaApplication.database?.mstCommonDao()
    }

   fun getMstCommon(flag: Int): List<MstCommonEntity> {
        return mstCommonDao!!.getMstCommon(flag)
    }

    fun getCount():Int {
        return mstCommonDao!!.getCount()
    }

    fun getMstCommondata(flag: Int): LiveData<List<MstCommonEntity>> {
        return mstCommonDao!!.getMstCommondata(flag)
    }




}