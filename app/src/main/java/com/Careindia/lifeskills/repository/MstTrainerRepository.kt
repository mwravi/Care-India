package com.careindia.lifeskills.repository

import android.app.Application
import com.careindia.lifeskills.application.CareIndiaApplication
import com.careindia.lifeskills.dao.MstTrainerDao
import com.careindia.lifeskills.dao.TrainingDao
import com.careindia.lifeskills.entity.MstTrainerEntity

class MstTrainerRepository {
    var mstTrainerDao: MstTrainerDao? = null

    constructor(application: Application) {
        mstTrainerDao = CareIndiaApplication.database?.mstTrainerDao()
    }
    fun getTrainerName(trainerId:List<String>):List<String>{
        return mstTrainerDao!!.getTrainerName(trainerId)
    }
}