package com.careindia.lifeskills.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.careindia.lifeskills.entity.MstZoneEntity
import com.careindia.lifeskills.repository.MstZoneRepository

class MstZoneViewModel : AndroidViewModel {
    var mstZoneRepository: MstZoneRepository? = null

    constructor(application: Application) : super(application) {
        mstZoneRepository = MstZoneRepository(application)
    }


    fun getMstZoneLive(DistrictCode:Int): LiveData<List<MstZoneEntity>> {
        return mstZoneRepository!!.getMstZoneLive(DistrictCode)
    }

    fun getMstZone(DistrictCode:Int): List<MstZoneEntity> {
        return mstZoneRepository!!.getMstZone(DistrictCode)
    }


}