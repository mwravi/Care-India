package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstTrainerEntity
import com.careindia.lifeskills.repository.MstTrainerRepository


class MstTrainerViewModel: AndroidViewModel {
    var mstTrainerRepository: MstTrainerRepository? = null

    constructor(application: Application) : super(application) {
        mstTrainerRepository = MstTrainerRepository(application)
    }

    fun getTrainerName(trainerId:List<String>):List<String>{
       return mstTrainerRepository!!.getTrainerName(trainerId)
    }
}