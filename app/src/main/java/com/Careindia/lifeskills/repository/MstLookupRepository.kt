package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstLocalityDao
import com.careindia.lifeskills.dao.MstLookupDao
import com.careindia.lifeskills.entity.MstLocalityEntity
import com.careindia.lifeskills.entity.MstLookupEntity

class MstLookupRepository {
    var mstLookupDao: MstLookupDao?=null
    constructor(application: Application) {
        mstLookupDao = CareIndiaApplication.database?.mstLookupDao()
    }

   fun getMstLookup(LookupFlag:Int,Language:Int): List<MstLookupEntity> {
        return mstLookupDao!!.getMstLookup(LookupFlag,Language)
    }







}