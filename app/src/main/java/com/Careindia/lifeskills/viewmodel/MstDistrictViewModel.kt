package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstDistrictEntity
import com.careindia.lifeskills.repository.MstDistrictRepository

class MstDistrictViewModel : AndroidViewModel {
    var mstDistrictRepository: MstDistrictRepository? = null

    constructor(application: Application) : super(application) {
        mstDistrictRepository = MstDistrictRepository(application)
    }

    fun getMstUser(StateCode:Int): List<MstDistrictEntity> {
        return mstDistrictRepository!!.getMstDistrict(StateCode)
    }



}