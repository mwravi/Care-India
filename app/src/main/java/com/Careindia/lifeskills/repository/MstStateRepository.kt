package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstStateDao
import com.careindia.lifeskills.entity.MstStateEntity

class MstStateRepository {
    var mstStateDao: MstStateDao?=null
    constructor(application: Application) {
        mstStateDao = CareIndiaApplication.database?.mstStateDao()
    }

   fun getMstState(): List<MstStateEntity> {
        return mstStateDao!!.getMstState()
    }







}