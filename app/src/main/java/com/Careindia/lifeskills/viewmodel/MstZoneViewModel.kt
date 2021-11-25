package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.MstZoneRepository

class MstZoneViewModel : AndroidViewModel {
    var mstZoneRepository: MstZoneRepository? = null

    constructor(application: Application) : super(application) {
        mstZoneRepository = MstZoneRepository(application)
    }

    fun getMstUser(StateCode:Int,DistrictCode:Int): List<MstZoneEntity> {
        return mstZoneRepository!!.getMstZone(StateCode,DistrictCode)
    }



}