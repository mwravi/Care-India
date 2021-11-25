package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstLocalityEntity
import com.careindia.lifeskills.repository.MstLocalityRepository

class MstLocalityViewModel : AndroidViewModel {
    var mstLocalityRepository: MstLocalityRepository? = null

    constructor(application: Application) : super(application) {
        mstLocalityRepository = MstLocalityRepository(application)
    }

    fun getMstUser(StateCode:Int,DistrictCode:Int,ZoneCode:Int,PWCode:Int): List<MstLocalityEntity> {
        return mstLocalityRepository!!.getMstLocality(StateCode,DistrictCode,ZoneCode,PWCode)
    }



}